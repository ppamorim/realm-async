package async.realm.ppamorim.com.realmasync;

import com.github.ppamorim.threadexecutor.InteractorExecutor;
import com.github.ppamorim.threadexecutor.MainThread;
import com.github.ppamorim.threadexecutor.MainThreadImpl;
import com.github.ppamorim.threadexecutor.ThreadExecutor;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * This class generate the provider of the
 * thread needed to use to have a reference
 * of the main thread and the ThreadExecutor.
 */
@Module public class ApplicationModule {

  @Provides @Singleton InteractorExecutor provideThreadExecutor(ThreadExecutor executor) {
    return executor;
  }

  @Provides @Singleton MainThread providePostExecutionThread(MainThreadImpl mainThread) {
    return mainThread;
  }

}