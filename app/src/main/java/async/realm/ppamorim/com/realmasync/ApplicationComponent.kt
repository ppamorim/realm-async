package async.realm.ppamorim.com.realmasync

import com.github.ppamorim.threadexecutor.InteractorExecutor
import com.github.ppamorim.threadexecutor.MainThread
import dagger.Component
import javax.inject.Singleton

/**
 * This component provides the instance of
 * essential instances on the application,
 * such threads and web requester.
 */
@Singleton @Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
  fun inject(realmAsyncApplication: RealmAsyncApplication)
  fun executor(): InteractorExecutor
  fun mainThread(): MainThread
}