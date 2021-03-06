package com.abhat.yifycleanarchitecture.presentation.presenter;

import com.abhat.yifycleanarchitecture.data.model.ApiResponseData;
import com.abhat.yifycleanarchitecture.domain.usecases.UseCase;
import com.abhat.yifycleanarchitecture.presentation.view.MovieListView;

import rx.Subscriber;

/**
 * Created by Anirudh Uppunda on 3/9/17.
 */

public class MovieListPresenterImpl implements MovieListPresenter {

    private MovieListView mMovieListView;
    //Use cases
    private UseCase mGetMovieListUseCase;

    public MovieListPresenterImpl(MovieListView movieListView, UseCase getMovieListUseCase) {
        this.mMovieListView = movieListView;
        this.mGetMovieListUseCase = getMovieListUseCase;
    }

    @Override
    public void getMovieList(String limit, String searchQuery) {
        mGetMovieListUseCase.setMovieListParams(limit, searchQuery);
        mGetMovieListUseCase.execute()
                .subscribe(new Subscriber<ApiResponseData>() {
                    @Override
                    public void onCompleted() {
                        //Log.d("YIFY", "ON COMPLETE CALLED");
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mMovieListView != null) {
                            e.printStackTrace();
                            mMovieListView.displayError();
                        }
                    }

                    @Override
                    public void onNext(ApiResponseData data) {
                        if (mMovieListView != null) {
                            mMovieListView.displayMovieList(data.getData().getMovies());
                        }
                    }
                });
    }
}
