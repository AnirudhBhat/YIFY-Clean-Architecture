package com.abhat.yifycleanarchitecture;

import com.abhat.yifycleanarchitecture.data.model.ApiResponseData;
import com.abhat.yifycleanarchitecture.data.model.Data;
import com.abhat.yifycleanarchitecture.data.model.Movie;
import com.abhat.yifycleanarchitecture.domain.usecases.UseCase;
import com.abhat.yifycleanarchitecture.presentation.presenter.MovieListPresenterImpl;
import com.abhat.yifycleanarchitecture.presentation.view.MovieListView;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Anirudh Uppunda on 5/9/17.
 */

public class MovieListPresenterImplTest {
    private MockView view;

    @Test
    public void shouldPassMovieListToView() {
        // given
        view = new MockView();
        UseCase<ApiResponseData> useCase = new MockUseCase();


        // when
        MovieListPresenterImpl moviePresenter = new MovieListPresenterImpl(view, useCase);
        moviePresenter.getMovieList("seeds", "");

        // then
        Assert.assertEquals(true, ((MockView)view).passed);
    }

    private class MockView implements MovieListView {
        boolean passed;
        @Override
        public void showLoading() {

        }

        @Override
        public void hideLoading() {
        }

        @Override
        public void displayMovieList(List<Movie> movies) {
            passed = true;
        }

        @Override
        public void displayError() {

        }
    }

    public class MockUseCase extends UseCase<ApiResponseData> {
        @Override
        public Observable<ApiResponseData> buildUseCase() {
            Observable<ApiResponseData> observable = Observable.fromCallable(new Callable<ApiResponseData>() {
                @Override
                public ApiResponseData call() throws Exception {
                    return new ApiResponseData();
                }
            });

              observable.subscribe(new Subscriber<ApiResponseData>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(ApiResponseData apiResponseData) {
                    view.displayMovieList(new ArrayList<Movie>());
                }
            });


            return observable;
        }
    }
}
