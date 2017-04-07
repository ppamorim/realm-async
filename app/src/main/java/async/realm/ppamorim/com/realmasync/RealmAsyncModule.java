package async.realm.ppamorim.com.realmasync;

import async.realm.ppamorim.com.realmasync.scopes.ActivityScope;
import dagger.Module;
import dagger.Provides;

@Module public class RealmAsyncModule {

  @Provides @ActivityScope DataInteractor provideInteractor(DataInteractorImpl dataInteractor) {
    return dataInteractor;
  }

}
