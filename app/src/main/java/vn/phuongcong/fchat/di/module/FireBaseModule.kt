package vn.phuongcong.fchat.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Ominext on 10/11/2017.
 */
@Module
class FireBaseModule {
    @Provides
    @Singleton
    fun getFireBaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
    @Provides
    @Singleton
    fun getFireBaseDataBase():FirebaseDatabase= FirebaseDatabase.getInstance()
}