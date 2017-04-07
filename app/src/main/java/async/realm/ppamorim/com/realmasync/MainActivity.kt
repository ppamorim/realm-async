package async.realm.ppamorim.com.realmasync

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import io.realm.RealmObject

/**
 * Dont try it at home please
 */
class MainActivity: AppCompatActivity() {

  var iAmCrazy = 0
  lateinit var mainThreadRealm: Realm

  override fun onCreate(savedInstanceState: Bundle?) {
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
      iAmCrazy += 1
      val dataToAdd = "data $iAmCrazy"
      UglyAsyncTask({

        println(mainThreadRealm.where(Item::class.java)
            .equalTo("data", dataToAdd)
            .findFirst()
            ?: "NOT FOUND")

        bugsOnTheTable()
      }).execute(dataToAdd)
    } else {
      DeleteAsyncTask().execute()
    }
  }

}

class UglyAsyncTask(val result: () -> Unit): AsyncTask<String, Any, Any?>() {

  override fun doInBackground(vararg data: String): Any? {
    Realm.getDefaultInstance().run {
      executeTransaction {
        createObject(Item::class.java).run {
          this.data = data[0]
        }
      }
      close()
    }
    return null
  }

  override fun onPostExecute(result: Any?) {
    super.onPostExecute(result)
    result()
  }

}

class DeleteAsyncTask: AsyncTask<Any, Any, Any?>() {

  override fun doInBackground(vararg data: Any): Any? {
    Realm.getDefaultInstance().run {
      executeTransaction {
        where(Item::class.java).findAll().deleteAllFromRealm()
      }
      close()
    }
    return null
  }

}

open class Item(var data: String): RealmObject() {

  constructor(): this("")

}
