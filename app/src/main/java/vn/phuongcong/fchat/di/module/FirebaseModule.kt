package vn.phuongcong.fchat.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Ominext on 10/12/2017.
 */
@Module
class FirebaseModule {
    @Provides
    @Singleton
    fun getFirebaseAuth():FirebaseAuth =FirebaseAuth.getInstance()

}