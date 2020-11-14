#Weekend Reports part II
# S.O.L.I.D principles and android introduction
## Single Responsibility Principle
## SINGLE RESPONSIBILITY
#### Single responsibility principle merupakan sebuah prinsip yang sering dipakai di dalam pembuatan aplikasi, dimana prinsip ini digunakan untuk mengatur tanggung jawab suatu class berdasarkan fungsionalitas.
## X - Single Responsibility Study Case
```KOTLIN
class MainActivity : AppCompatActivity(), MainView {
   private val viewModel by inject<MainViewModel>()

   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       ...

       onPrepare()
   }

   override fun onPrepare() {
       viewModel.getUsers()
   }
}
```
## √ - Single Responsibility Study Case
```KOTLIN
class MainActivity : AppCompatActivity(), MainView {
   private val viewModel by inject<MainViewModel>()

   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       ...

       onPrepare()
   }

   override fun onPrepare() {
       viewModel.getUsers()
   }
}
```
## OPEN / CLOSED
## Open / Closed Principle
#### Open / closed principle merupakan prinsip yang setiap class dan member di dalamnya harus terbuka untuk diwariskan, namun tertutup untuk dimodifikasi oleh kelas turunannya.
## X - Open / Closed Principle Case
```KOTLIN
class MainActivity : AppCompatActivity() {
   enum class State { ADD, SUBTRACT, MULTIPLE, DIVIDE }

   private var state = State.ADD

   override fun onCreate(savedInstanceState: Bundle?) {
       ...
       fabCount.setOnClickListener {
           var count = tvCounter.text.toString().toIntOrNull() ?: 0
           val value = etCounter.text.toString().toIntOrNull() ?: 0

           when (state) {
               State.DIVIDE -> count /= value
               State.MULTIPLE -> count *= value
               State.SUBTRACT -> count--
               else -> count++
           }

           tvCounter.text = count.toString()
       }
}
```
## √ - Open / Closed Principle Case
```KOTLIN
enum class State { ADD, SUBTRACT, MULTIPLE, DIVIDE }

interface Arithmetic {
   fun calculate(value: Int, otherValue: Int): Int
}

class Addition : Arithmetic {
   override fun calculate(value: Int, otherValue: Int) = value + 1
}

class Subtraction : Arithmetic {
   override fun calculate(value: Int, otherValue: Int) = value - 1
}

class Divide : Arithmetic {
   override fun calculate(value: Int, otherValue: Int) = value / otherValue
}

class Multiple : Arithmetic {
   override fun calculate(value: Int, otherValue: Int) = value * otherValue
}
```
## √ - Open / Closed Principle Case
```KOTLIN
class MainActivity : AppCompatActivity() {
   private var state = State.ADD

   override fun onCreate(savedInstanceState: Bundle?) {
       ...

       fabCount.setOnClickListener {
           val count = tvCounter.text.toString().toIntOrNull() ?: 0
           val value = etCounter.text.toString().toIntOrNull() ?: 0
           val arithmetic: Arithmetic = when (state) {
               State.DIVIDE -> Divide()
               State.MULTIPLE -> Multiple()
               State.SUBTRACT -> Subtraction()
               State.ADD -> Addition()
           }

           tvCounter.text = arithmetic.calculate(count, value).toString()
       }
   }
}
```
## LISKOV SUBSTITUTION
## Liskov Substitution Principle
#### Liskov substitution principle merupakan prinsip yang mengatur subclass harus meng-override method dari superclass tanpa harus merusak fungsionalitas dari superclass.
## X - Liskov Substitution Principle Case
```KOTLIN
class MainActivity : AppCompatActivity() {

   override fun onCreate(savedInstanceState: Bundle?) {
       ...

       val user = UserModel(1)

       if (user.level == 0) {
           see()
           delete()
       } else {
           see()
       }
   }

   private fun see() { ... }

   private fun delete() { ... }
}
```
## √ - Liskov Substitution Principle Case

```KOTLIN
data class UserModel(val level: Int)

interface UserPrivilege {
   fun see()
}
interface AdminPrivilege: UserPrivilege {
   fun delete()
}

class MainActivity : AppCompatActivity(), AdminPrivilege {
   override fun delete() = print("delete")
   override fun see() = print("see")

   override fun onCreate(savedInstanceState: Bundle?) {
       ...
       val user = UserModel(1)
       if (user.level == 1) see()
       else see(); delete()
   }
}
```
## INTERFACE SEGREGATION
## Interface Segregation Principle
#### Interface segregation principle adalah prinsip yang mengatur class untuk tidak mengimplementasikan function yang tidak dipakai. Interface dengan function yang memiliki spesifik function lebih baik daripada interface yang memiliki banyak function yang general.
## X - Interface Segregation Principle Case
```KOTLIN
class MainActivity : AppCompatActivity(), TextWatcher {

   override fun onCreate(savedInstanceState: Bundle?) {
       ...

       etText.addTextChangedListener(this)
   }

   override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
       TODO("Not yet implemented")
   }

   override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
       tvPalindrome.text = ""
   }

   override fun afterTextChanged(p0: Editable?) {
       TODO("Not yet implemented")
   }
}
```
## √ - Interface Segregation Principle Case
```KOTLIN
interface OnTextChangedListener : TextWatcher {
   override fun afterTextChanged(s: Editable?) { }
   override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
}

class MainActivity : AppCompatActivity(), OnTextChangedListener {

   override fun onCreate(savedInstanceState: Bundle?) {
       ...
      
       etText.addTextChangedListener(this)
   }

   override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
       print(s.toString())
   }
}
```
## DEPENDENCY INVERSION
## Dependency Inversion Principle
#### Dependency inversion principle adalah prinsip yang mengatur bahwa high level class (class yang memiliki kumpulan fungsionalitas) tidak boleh bergantung kepada low level class (class yang hanya berurusan dengan fungsionalitas yang detail).
## X - Dependency Inversion Principle Case
```KOTLIN
enum class NotifyType { EMAIL, WHATSAPP }

class JobNotifier {
   private val email = Email()
   private val wa = WhatsApp()

   fun notify(type: NotifyType) {
       if (type == NotifyType.EMAIL) email.sendAlert("You are alerted from EMAIL")
       else if (type == NotifyType.WHATSAPP) wa.sendAlert("You are alerted from WHATSAPP")
   }
}

class Email {
   fun sendAlert(alert: String) { }
}

class WhatsApp {
   fun sendAlert(alert: String) { }
}
```
## √ - Dependency Inversion Principle Case
```KOTLIN
class JobNotifier(private val notifier: Notifier) {
   fun notifyJob() {
       if (notifier is Email) notifier.sendAlert("You are alerted from EMAIL")
       else if (notifier is WhatsApp) notifier.sendAlert("You are alerted from WhatsApp")
   }
}

interface Notifier {
   fun sendAlert(alert: String)
}

class Email : Notifier {
   override fun sendAlert(alert: String) = print(alert)
}

class WhatsApp : Notifier {
   override fun sendAlert(alert: String) = print(alert)
}
```
# Android manifest, activity, handling dan actions
## Android Manifest
#### Setiap aplikasi android pasti memiliki file AndroidManifest.xml pada root project. Android Manifest mendeskripsikan informasi secara esensial tentang aplikasi ke Android build tools, Android operating system dan Google Play. Beberapa file features pada Android Manifest:
* Package name dan application ID
* App components
* Intent filters
* Icons and labels
* Permissions
* Device compatibility
* Uses feature
* Uses sdk

## File Android Manifest
```KOTLIN
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
   package="id.refactory.counterapp">

   <application
       android:allowBackup="true"
       android:icon="@mipmap/ic_launcher"
       android:label="@string/app_name"
       android:roundIcon="@mipmap/ic_launcher_round"
       android:supportsRtl="true"
       android:theme="@style/AppTheme">
       <activity android:name=".MainActivity">
           <intent-filter>
               <action android:name="android.intent.action.MAIN" />
               <category android:name="android.intent.category.LAUNCHER" />
           </intent-filter>
       </activity>
   </application>

</manifest>
```
## Activity
#### Activity adalah suatu hal yang berfokus pada satu hal yang dilakukan oleh user. Sebagian besar activity berinteraksi dengan user, seperti click, scroll, drag dsb.

## Activity memiliki daur hidup mulai dari activity itu dibuat sampai dihancurkan.
* onCreate: activity dibuat, namun belum terlihat oleh user.
* onStart: activity terlihat, namun belum siap menerima interaksi user.
* onResume: activity berada di depan dan siap menerima interaksi user.
* onPause: activity berada di belakang dan tidak berinteraksi dengan user.
* onStop: activity tidak lagi terlihat oleh user.
* onDestroy: activity tidak lagi tersimpan di memory.

## Handling and Actions
#### Android dapat menerima beberapa macam input dari user, seperti: onClick, OnTextChanged, onItemSelected, onCheckedChange dsb.

## Menu, Dialog dan App Icon
## Menus
#### Menu adalah komponen antarmuka pengguna yang banyak dijumpai di berbagai jenis aplikasi, menu memberikan kemudahan kepada user dalam memilih tindakan di dalam aplikasi.
* Ada beberapa macam menu yang bisa digunakan di dalam Android:
* Options menu dan app bar.
* Context menu dan contextual action mode.
* Popup menu

## Dialogs
#### Dialog adalah tampilan kecil yang digunakan untuk meminta user untuk memilih tindakan atau memasukkan informasi tambahan. Dialog tidak mengisi secara penuh layar android dan secara normal digunakan user untuk melakukan tindakan sebelum memproses keinginan user.
* AlertDialog
* DatePickerDialog atau TimePickerDialog

## App Icon
#### App Icon pada Android mendukung untuk berbagai macam tema yang dimiliki oleh user, seperti icon circle, square, legacy dsb. 

## RecyclerView AndroidX, Adapter, ViewHolder.
## RecyclerView
#### RecyclerView adalah sebuah scrollable view yang menampilkan data berupa iterable pada Android, RecyclerView merupakan versi yang lebih canggih dari ListView dimana RecyclerView akan melakukan binding pada data yang terlihat saja.

```KOTLIN implementation 'androidx.recyclerview:recyclerview:1.1.0'
```
## Pemanggilan RecyclerView di dalam XML Android:
```KOTLIN <androidx.recyclerview.widget.RecyclerView xmlns:android="http://schemas.android.com/apk/res/android"
   xmlns:app="http://schemas.android.com/apk/res-auto"
   android:id="@+id/rvList"
   android:layout_width="match_parent"
   android:layout_height="match_parent"
   app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager" />
```
## RecyclerView Adapter
#### RecyclerView adapter digunakan untuk memasukkan data iterable ke dalam RecyclerView, mengganti data iterable yang baru, mengganti tampilan saat konten tidak terlihat dan hanya menampilkan data pada konten yang terlihat saja.
```KOTLIN
class NameAdapter : RecyclerView.Adapter<NameAdapter.ViewHolder>() {

   class ViewHolder(view: View) : RecyclerView.ViewHolder(view) { }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       TODO("Not yet implemented")
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) { TODO("Not yet implemented") }

   override fun getItemCount(): Int { TODO("Not yet implemented") }
}
```
## RecyclerView ViewHolder
#### RecyclerView ViewHolder adalah sebuah class yang digunakan untuk melakukan proses binding data pada setiap item pada RecyclerView di android.
```KOTLIN
class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
   fun bindData(s: String) {
       val tvName = itemView.findViewById<TextView>(android.R.id.text1)
       tvName.text = s
   }
}
```
## Intent, Intent Filters dan Parcels
## Intent
#### Intent adalah object perpesanan yang digunakan untuk meminta tindakan dari komponen, intent juga memfasilitasi komunikasi antar komponen dalam berbagai cara:
* Starting an activity
* Starting a service
* Delivering a broadcast

## Start an Activity
#### Intent memfasilitasi dalam melakukan pemanggilan activity baru, pada startActivity meminta sebuah input parameter berupa intent. Intent dapat juga membawa data antar activity.
```KOTLIN
val intent = Intent(this, SecondActivity::class.java).putExtra(DATA, 100)
startActivity(intent)
```
## Intent Filters
## Intent filter merupakan ekspresi dari sebuah komponen di dalam file manifest pada aplikasi Android. Intent filter digunakan untuk menentukan jenis intent yang diterima oleh komponen.
```KOTLIN
<activity android:name=".MainActivity">
   <intent-filter>
       <action android:name="android.intent.action.MAIN" />
       <category android:name="android.intent.category.LAUNCHER" />
   </intent-filter>
</activity>
```
## Intent Filters
```KOTLIN
<activity android:name=".SecondActivity">
   <intent-filter>
       <action android:name="android.intent.action.SEND"/>
       <category android:name="android.intent.category.DEFAULT"/>
       <data android:mimeType="text/plain"/>
   </intent-filter>
   <intent-filter>
       <action android:name="android.intent.action.SEND"/>
       <action android:name="android.intent.action.SEND_MULTIPLE"/>
       <category android:name="android.intent.category.DEFAULT"/>
       <data android:mimeType="image/*"/>
       <data android:mimeType="video/*"/>
   </intent-filter>
</activity>
```
## Parcels
#### Sebuah object bundle yang digunakan untuk memberikan data berupa object kepada komponen Android lainnya berdasarkan key.
```KOTLIN
@Parcelize
data class User(val name: String, val age: Int) : Parcelable
override fun onCreate(savedInstanceState: Bundle?) {
   super.onCreate(savedInstanceState)
   setContentView(R.layout.activity_main)
   btnNext.setOnClickListener {
       val user = User("Angga Saputra", 20)
       val intent = Intent(this, SecondActivity::class.java).apply { putExtra(DATA, user) }
       startActivity(intent)
   }
}
```
# Recyclerview and MVP
# Recyclerview
#### RecyclerView adalah sebuah scrollable view yang menampilkan data berupa iterable pada Android, RecyclerView merupakan versi yang lebih canggih dari ListView dimana RecyclerView akan melakukan binding pada data yang terlihat saja.
```KOTLIN
implementation 'androidx.recyclerview:recyclerview:1.1.0'
Pemanggilan RecyclerView di dalam XML Android:
<androidx.recyclerview.widget.RecyclerView xmlns:android="http://schemas.android.com/apk/res/android"
   xmlns:app="http://schemas.android.com/apk/res-auto"
   android:id="@+id/rvList"
   android:layout_width="match_parent"
   android:layout_height="match_parent"
   app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager" />
```
# RecyclerView Adapter
#### RecyclerView adapter digunakan untuk memasukkan data iterable ke dalam RecyclerView, mengganti data iterable yang baru, mengganti tampilan saat konten tidak terlihat dan hanya menampilkan data pada konten yang terlihat saja.
```KOTLIN
class NameAdapter : RecyclerView.Adapter<NameAdapter.ViewHolder>() {

   class ViewHolder(view: View) : RecyclerView.ViewHolder(view) { }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       TODO("Not yet implemented")
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) { TODO("Not yet implemented") }

   override fun getItemCount(): Int { TODO("Not yet implemented") }
}
```
# RecyclerView ViewHolder
#### RecyclerView ViewHolder adalah sebuah class yang digunakan untuk melakukan proses binding data pada setiap item pada RecyclerView di android.
```KOTLIN
class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
   fun bindData(s: String) {
       val tvName = itemView.findViewById<TextView>(android.R.id.text1)
       tvName.text = s
   }
}
```
# SharedPreferences
#### SharedPreferences adalah penyimpanan lokal untuk small data collection yang mempunyai key dan value pada Android.
#### SharedPreferences menyimpan data yang bersifat simple seperti Int, Boolean, String, Float, dsb. Dalam menyimpan dan mengubah data di SharedPreferences perlu memanggil API SharedPreferences.Editor kemudian apply penambahan dan perubahannya.
```KOTLIN 
private const val SHARED_TODO = "id.refactory.todolistapp"
private lateinit var shared: SharedPreferences

fun getPref(context: Context): SharedPreferences {
   if (!this::shared.isInitialized) shared =
       context.getSharedPreferences(SHARED_TODO, Context.MODE_PRIVATE)
   return shared
}
```
# Save data
#### SharedPreferences menyimpan data yang bersifat simple seperti Int, Boolean, String, Float, dsb. Dalam menyimpan dan mengubah data di SharedPreferences perlu memanggil API SharedPreferences.Editor kemudian apply penambahan dan perubahannya.
```KOTLIN 
inline fun <reified T> SharedPreferences.save(key: String, value: T) {
   edit {
       when (value) {
           is Boolean -> putBoolean(key, value)
           is Int -> putInt(key, value)
           is String -> putString(key, value)
           is Long -> putLong(key, value)
           is Float -> putFloat(key, value)
           else -> println("Don't save data with this data type, instead of save on local database")
       }
   }
}
val pref = SharedUtil.getPref(this)
pref.save("Boolean", true)
pref.save("Int", 1000)
pref.save("String", "Hello world")
pref.save("Float", 10.0f)
```

# Read Data
#### Mengambil data pada SharedPreferences harus eksplisit tipe datanya, semisal mengambil data String maka harus menggunakan method getString(key). SharedPreferences juga menyediakan input untuk default valuenya, ketika data yang disimpan adalah null atau tidak ada.
```KOTLIN
inline fun <reified T> SharedPreferences.save(key: String, value: T) {
   edit {
       when (value) {
           is Boolean -> putBoolean(key, value)
           is Int -> putInt(key, value)
           is String -> putString(key, value)
           is Long -> putLong(key, value)
           is Float -> putFloat(key, value)
           else -> println("Don't save data with this data type, instead of save on local database")
       }
   }
}
val pref = SharedUtil.getPref(this)
pref.save("Boolean", true)
pref.save("Int", 1000)
pref.save("String", "Hello world")
pref.save("Float", 10.0f)
```
 ## Fragment, Fragment Lifecycle, Frame Layout dan Fragment Transaction.
## Fragment
#### Fragment mewakili porsi dari user interface pada sebuah FragmentActivity, Fragment mensegmentasi aplikasi menjadi beberapa layar independen yang dikumpulkan dalam suatu Activity.
## Fragment Lifecycle
#### Lifecycle dari Fragment hampir sama dengan Activity, namun fragment memiliki tambahan lifecycle dalam berhubungan dengan Activity. Lifecycle Fragment berjalan bersamaan dengan lifecycle Activity.

## Create a Fragment
#### Membuat fragment hampir sama dengan membuat Activity, namun fragment tidak perlu di daftarkan di AndroidManifest.xml.
```KOTLIN
class BlankFragment : Fragment() {
   override fun onCreateView(
       inflater: LayoutInflater, container: ViewGroup?,
       savedInstanceState: Bundle?
   ): View? {
       val view = inflater.inflate(R.layout.fragment_blank, container, false)
       view.setOnClickListener { Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show() }
       return view
   }
}
```
## Create a FrameLayout
#### FrameLayout adalah view berupa container yang akan menampung fragment pada Activity sedang berjalan.
```KOTLIN
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
   xmlns:app="http://schemas.android.com/apk/res-auto"
   xmlns:tools="http://schemas.android.com/tools"
   android:id="@+id/flMain"
   android:layout_width="match_parent"
   android:layout_height="match_parent"
   tools:context=".MainActivity"/>
```
## Fragment Transition
#### FragmentManager class dan FragmentTransaction class digunakan untuk menambahkan, menghapus dan mengganti fragment di FrameLayout di dalam Activity pada saat runtime.
```KOTLIN
supportFragmentManager.beginTransaction().add(R.id.flMain, BlankFragment()).commit()
supportFragmentManager.beginTransaction().replace(R.id.flMain, BlankFragment()).commit()
supportFragmentManager.beginTransaction().remove(BlankFragment())
```
