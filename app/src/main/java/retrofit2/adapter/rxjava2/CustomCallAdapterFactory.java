package retrofit2.adapter.rxjava2;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.CustomOkHttpCall;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 自定义RxCallAdapterFactory
 * @tips 实现http-code为4xx时，格式若为JSON则不抛异常
 * @version imkarl 2017-04
 */
public class CustomCallAdapterFactory extends CallAdapter.Factory {

    /**
     * Returns an instance which creates synchronous observables that do not operate on any scheduler
     * by default.
     */
    public static CustomCallAdapterFactory create() {
        return new CustomCallAdapterFactory(null, false);
    }

    private final Scheduler scheduler;
    private final boolean isAsync;

    private CustomCallAdapterFactory(Scheduler scheduler, boolean isAsync) {
        this.scheduler = scheduler;
        this.isAsync = isAsync;
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        Class<?> rawType = getRawType(returnType);

        if (rawType == Completable.class) {
            // Completable is not parameterized (which is what the rest of this method deals with) so it
            // can only be created with a single configuration.
            return createCallAdapter(Void.class, scheduler, isAsync, false, true, false, false,
                    false, true);
        }

        boolean isFlowable = rawType == Flowable.class;
        boolean isSingle = rawType == Single.class;
        boolean isMaybe = rawType == Maybe.class;
        if (rawType != Observable.class && !isFlowable && !isSingle && !isMaybe) {
            return null;
        }

        boolean isResult = false;
        boolean isBody = false;
        Type responseType;
        if (!(returnType instanceof ParameterizedType)) {
            String name = isFlowable ? "Flowable"
                    : isSingle ? "Single"
                    : isMaybe ? "Maybe" : "Observable";
            throw new IllegalStateException(name + " return type must be parameterized"
                    + " as " + name + "<Foo> or " + name + "<? extends Foo>");
        }

        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        Class<?> rawObservableType = getRawType(observableType);
        if (rawObservableType == Response.class) {
            if (!(observableType instanceof ParameterizedType)) {
                throw new IllegalStateException("Response must be parameterized"
                        + " as Response<Foo> or Response<? extends Foo>");
            }
            responseType = getParameterUpperBound(0, (ParameterizedType) observableType);
        } else if (rawObservableType == Result.class) {
            if (!(observableType instanceof ParameterizedType)) {
                throw new IllegalStateException("Result must be parameterized"
                        + " as Result<Foo> or Result<? extends Foo>");
            }
            responseType = getParameterUpperBound(0, (ParameterizedType) observableType);
            isResult = true;
        } else {
            responseType = observableType;
            isBody = true;
        }

        return createCallAdapter(responseType, scheduler, isAsync, isResult, isBody, isFlowable,
                isSingle, isMaybe, false);
    }

    private CallAdapter<?, ?> createCallAdapter(Type responseType, Scheduler scheduler,
                                                boolean isAsync, boolean isResult, boolean isBody, boolean isFlowable,
                                                boolean isSingle, boolean isMaybe, boolean isCompletable) {
        return new RxJava2CallAdapter(responseType, scheduler, isAsync, isResult, isBody, isFlowable,
                isSingle, isMaybe, isCompletable);
    }

    private static class RxJava2CallAdapter<R> implements CallAdapter<R, Object> {
        private final Type responseType;
        private final Scheduler scheduler;
        private final boolean isAsync;
        private final boolean isResult;
        private final boolean isBody;
        private final boolean isFlowable;
        private final boolean isSingle;
        private final boolean isMaybe;
        private final boolean isCompletable;

        RxJava2CallAdapter(Type responseType, Scheduler scheduler, boolean isAsync, boolean isResult,
                           boolean isBody, boolean isFlowable, boolean isSingle, boolean isMaybe,
                           boolean isCompletable) {
            this.responseType = responseType;
            this.scheduler = scheduler;
            this.isAsync = isAsync;
            this.isResult = isResult;
            this.isBody = isBody;
            this.isFlowable = isFlowable;
            this.isSingle = isSingle;
            this.isMaybe = isMaybe;
            this.isCompletable = isCompletable;
        }

        @Override public Type responseType() {
            return responseType;
        }

        Observable<Response<R>> handleObservable(Observable<Response<R>> observable) {
            return observable;
        }

        @Override public Object adapt(Call<R> call) {
            call = CustomOkHttpCall.get(call);

            Observable<Response<R>> responseObservable = handleObservable(isAsync
                    ? new CallEnqueueObservable<>(call)
                    : new CallExecuteObservable<>(call));

            Observable<?> observable;
            if (isResult) {
                observable = new ResultObservable<>(responseObservable);
            } else if (isBody) {
                observable = new BodyObservable<>(responseObservable);
            } else {
                observable = responseObservable;
            }

            if (scheduler != null) {
                observable = observable.subscribeOn(scheduler);
            }

            if (isFlowable) {
                return observable.toFlowable(BackpressureStrategy.LATEST);
            }
            if (isSingle) {
                return observable.singleOrError();
            }
            if (isMaybe) {
                return observable.singleElement();
            }
            if (isCompletable) {
                return observable.ignoreElements();
            }
            return observable;
        }
    }

}