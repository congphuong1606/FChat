package vn.phuongcong.fchat.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
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
    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseUser(): FirebaseUser = FirebaseAuth.getInstance().currentUser!!

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @Provides
    @Singleton
    fun provideDatabaseReference(): DatabaseReference = FirebaseDatabase.getInstance().reference

    @Provides
    fun provideStorageReference(): StorageReference = FirebaseStorage.getInstance().reference
}