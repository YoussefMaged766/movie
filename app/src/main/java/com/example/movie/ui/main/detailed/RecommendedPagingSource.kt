package com.example.movie.ui.main.detailed

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movie.models.movie
import com.example.movie.util.WebServices
import retrofit2.HttpException
import java.io.IOException

private const val TMDB_STARTING_PAGE_INDEX = 1

class RecommendedPagingSource(val id:Int,val webservices: WebServices) : PagingSource<Int, movie>() {
    override fun getRefreshKey(state: PagingState<Int, movie>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, movie> {

        val pageIndex = params.key ?: TMDB_STARTING_PAGE_INDEX

        return try {
            Log.e("load: ",Thread.currentThread().name )
            val responsePopular = webservices.get_recommended(id,page = pageIndex)
            val movies = responsePopular.results
            val nextKey = if (movies.isEmpty()) null else pageIndex + 1
            val prevKey =  if (pageIndex == TMDB_STARTING_PAGE_INDEX) null else pageIndex - 1

            LoadResult.Page(
                data =  movies,
                nextKey = nextKey,
                prevKey = prevKey
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}