package com.example.movie.ui.main.trend

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movie.models.ResultsItem_trendTV
import com.example.movie.models.movie
import com.example.movie.util.WebServices
import retrofit2.HttpException
import java.io.IOException

private const val TMDB_STARTING_PAGE_INDEX = 1

class TVTrendPagingSource(val webservices: WebServices) : PagingSource<Int, ResultsItem_trendTV>() {
    override fun getRefreshKey(state: PagingState<Int, ResultsItem_trendTV>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem_trendTV> {

        val pageIndex = params.key ?: TMDB_STARTING_PAGE_INDEX

        return try {
            Log.e("load: ",Thread.currentThread().name )
            val responsePopular = webservices.get_trend_tv(page = pageIndex)
            val tv = responsePopular.results
            val nextKey = if (tv!!.isEmpty()) null else pageIndex + 1
            val prevKey =  if (pageIndex == TMDB_STARTING_PAGE_INDEX) null else pageIndex - 1

            LoadResult.Page(
                data =  tv,
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