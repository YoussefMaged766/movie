package com.example.movie.ui.main.toprated

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movie.models.movie
import com.example.movie.util.constants
import com.example.movie.util.webservices
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.max

private const val TMDB_STARTING_PAGE_INDEX = 1

class MoviesPagingSource(val webservices: webservices) : PagingSource<Int, movie>() {



    override fun getRefreshKey(state: PagingState<Int, movie>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
//        }
//        val anchorPosition = state.anchorPosition ?: return null
//        val movie = state.closestItemToPosition(anchorPosition) ?: return null
//        return ensureValidKey( movie.id !! - (state.config.pageSize / 2))
        TODO("not")
//        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, movie> {
        val pageIndex = params.key ?: TMDB_STARTING_PAGE_INDEX
        val range = pageIndex.until(pageIndex + params.loadSize)
        return try {
//            delay(500)
            val response = webservices.getTopRatedmoviespaging(page = pageIndex)
            val movies = response.results
            val nextKey =
                if (movies.isEmpty()) {
                    null
                } else {pageIndex + 1
                    // By default, initial load size = 3 * NETWORK PAGE SIZE
                    // ensure we're not requesting duplicating items at the 2nd request
//                    pageIndex + (params.loadSize / 25)
//                    range.last+1
                }
            Log.e("load: ", pageIndex.toString())
            LoadResult.Page(
                data = movies,
//                prevKey = if (pageIndex == TMDB_STARTING_PAGE_INDEX) null else pageIndex,
//                when(pageIndex){
//                    TMDB_STARTING_PAGE_INDEX->null
//                    else->ensureValidKey(key = range.first-params.loadSize)
//                }
                if (pageIndex == TMDB_STARTING_PAGE_INDEX) null else pageIndex - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }


    }
}
