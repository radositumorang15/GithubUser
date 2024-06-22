package com.dicoding.githubuser.model
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.githubuser.data.response.DetailUserResponse
import com.dicoding.githubuser.data.retrofit.ApiConfig
import com.dicoding.githubuser.database.FavoriteUserDao
import com.dicoding.githubuser.database.FavoriteUserDatabase
import com.dicoding.githubuser.entity.FavoriteUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class DetailViewModel(application: Application) : AndroidViewModel(application)  {
    private val mUserDao: FavoriteUserDao

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }
    init {
        val favdb = FavoriteUserDatabase.getInstance(application)
        mUserDao = favdb.favoriteDao()
    }


    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse>
        get() = _detailUser

    fun setFavorite(item: FavoriteUser){
        CoroutineScope(Dispatchers.IO).launch{
            mUserDao.addFavorite(item)
        }
    }

    fun findFavorite(id: Int): LiveData<FavoriteUser> = mUserDao.findById(id)

    fun deleteFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch{
            mUserDao.deleteFavorite(id)
        }
    }

    fun getDetailUser(username: String) {
        ApiConfig.getApiService().getDetailUser(username).enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(call: Call<DetailUserResponse>, response: Response<DetailUserResponse>) {
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {

                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
            }
        })
    }
}
