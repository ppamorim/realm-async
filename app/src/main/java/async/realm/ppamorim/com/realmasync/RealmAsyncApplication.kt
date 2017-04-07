package async.realm.ppamorim.com.realmasync

import android.app.Application

class RealmAsyncApplication: Application() {

  val applicationComponent: ApplicationComponent by lazy {
    DaggerApplicationComponent.builder()
        .applicationModule(ApplicationModule())
        .build()
  }

  override fun onCreate() {
    applicationComponent.inject(this)
    super.onCreate()
  }

}