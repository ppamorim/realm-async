package async.realm.ppamorim.com.realmasync

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.ppamorim.threadexecutor.Interactor
import com.github.ppamorim.threadexecutor.InteractorExecutor
import com.github.ppamorim.threadexecutor.MainThread
import io.realm.Realm
import javax.inject.Inject

class ThreadPoolActivity: AppCompatActivity() {

  val realmAsyncComponent: RealmAsyncComponent by lazy {
    DaggerRealmAsyncComponent.builder()
        .applicationComponent((applicationContext as RealmAsyncApplication).applicationComponent)
        .realmAsyncModule(RealmAsyncModule())
        .build()
  }

  var iAmCrazy = 0
  lateinit var mainThreadRealm: Realm

  @Inject lateinit var dataInteractor: DataInteractor

  override fun onCreate(savedInstanceState: Bundle?) {
    realmAsyncComponent.inject(this)
    super.onCreate(savedInstanceState)
    Realm.init(this)
    setContentView(R.layout.activity_main)
    mainThreadRealm = Realm.getDefaultInstance()
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    bugsOnTheTable()
  }

  override fun onDestroy() {
    mainThreadRealm.close()
    super.onDestroy()
  }

  /**
   * This method was projected to be ugly, sorry about that, today is friday.
   */
  fun bugsOnTheTable() {

    if (iAmCrazy < 50) {

      val dataToAdd = "data $iAmCrazy"

      iAmCrazy += 1

      dataInteractor.load(dataToAdd) {

        println(mainThreadRealm.where(Item::class.java)
            .equalTo("data", dataToAdd)
            .findFirst()
            ?: "NOT FOUND")
        bugsOnTheTable()

      }
    } else {
      DeleteAsyncTask().execute()
    }
  }

}

interface DataInteractor {
  fun load(data: String, callback: () -> Unit)
}

class DataInteractorImpl @Inject constructor(
    val mainThread: MainThread,
    val interactorExecutor: InteractorExecutor):
    Interactor, DataInteractor {

  lateinit var callback: () -> Unit
  lateinit var data: String

  override fun load(data: String, callback: () -> Unit) {
    this.data = data
    this.callback = callback
    interactorExecutor.run(this)
  }

  override fun run() {
    Realm.getDefaultInstance().run {
      executeTransaction {
        createObject(Item::class.java).data = data
      }
      close()
    }
    mainThread.post { callback() }
  }

}