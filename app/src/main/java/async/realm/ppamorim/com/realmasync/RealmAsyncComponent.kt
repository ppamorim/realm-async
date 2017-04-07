package async.realm.ppamorim.com.realmasync

import async.realm.ppamorim.com.realmasync.scopes.ActivityScope
import dagger.Component

/**
 * This component provides the instance of
 * essential instances on the application,
 * such threads and web requester.
 */
@ActivityScope @Component(modules = arrayOf(RealmAsyncModule::class),
    dependencies = arrayOf(ApplicationComponent::class))
interface RealmAsyncComponent {
  fun inject(threadPoolActivity: ThreadPoolActivity)
  fun dataInteractor(): DataInteractor
}