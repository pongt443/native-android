package com.example.androidnative


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.androidnative.App.Companion.CHANNEL
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel

class MainActivity : AppCompatActivity() {

    lateinit var option: Spinner
    lateinit var result: TextView
    lateinit var button: Button
    lateinit var tokenNow: String

    // The FlutterEngine is the container through which Dart code can be run in an Android application.
    private lateinit var flutterEngine: FlutterEngine

    // A named channel for communicating with the Flutter application using asynchronous method calls.
    private lateinit var methodChannel: MethodChannel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Call first for register flutter
//        initFlutterEngine()
//        // Call after initFlutterEngine() cause Met hodChannel need flutterEngine
//        initMethodChannel()
        // Call in fragment
//        initFlutterFragment()

        option = findViewById(R.id.sp_option) as Spinner
        result = findViewById(R.id.tv_result) as TextView
        button = findViewById(R.id.Btflutter) as Button

        val options = arrayOf("66659332061", "66659332051", "66937060046", "66937060048", "QA1", "QA2", "QA3", "QA4", "QA5", "QA6", "QA7", "QA8", "QA9", "QA10")
        val token = arrayOf(
            "eyJhbGciOiJIUzI1NiJ9.eyJtb2JpbGVObyI6IjA2NTkzMzIwNjEiLCJuZXR3b3JrVHlwZSI6Ik1BU1MiLCJ0aW1lc3RhbXAiOiIyMDIzLTAzLTIzVDE0OjU0OjMyKzA3OjAwIn0.R4eHmEG4oP4pZnKl5bCZPdjDBkhkUzrCBdh1rVZKlJE",
            "eyJhbGciOiJIUzI1NiJ9.eyJtb2JpbGVObyI6IjA2NTkzMzIwNjEiLCJuZXR3b3JrVHlwZSI6Ik1BU1MiLCJ0aW1lc3RhbXAiOiIyMDIzLTAzLTIxVDE0OjIzOjU3KzA3OjAwIn0.HXCVtTlMCrNA18x1zO73jee8z6ADfGkFrBA1recYdBI",
            "eyJhbGciOiJIUzI1NiJ9.eyJtb2JpbGVObyI6IjA2NTkzMzIwNjEiLCJuZXR3b3JrVHlwZSI6Ik1BU1MiLCJ0aW1lc3RhbXAiOiIyMDIzLTAzLTIxVDE0OjI0OjQ5KzA3OjAwIn0.tBmXHX3zHWid_-kP57PgB8rivPvt0z90mrTOHAFfMp4",
            "eyJhbGciOiJIUzI1NiJ9.eyJtb2JpbGVObyI6IjA2NTkzMzIwNjEiLCJuZXR3b3JrVHlwZSI6Ik1BU1MiLCJ0aW1lc3RhbXAiOiIyMDIzLTAzLTIxVDE0OjI1OjM1KzA3OjAwIn0.9FtGXsVL-9KHe8pCCcVYh4XwBIbzE-y0d6pJANA4KLM",
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMSIsImlhdCI6MTUxNjIzOTAyMn0.dbYFJPSwNzNj-vPlexSW08gb6yP9q-6LSuFBz746TZE",
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMiIsImlhdCI6MTUxNjIzOTAyMn0.wZ8DmfL8ikHSpiJanN9IhzEEDTQ7Q2alkKiyNMgu64k",
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMyIsImlhdCI6MTUxNjIzOTAyMn0.MCIzx1QlGbLlYVW5GTn6FrL0gt9gWBhcckC_l1jgweE",
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBNCIsImlhdCI6MTUxNjIzOTAyMn0.AYUcoGLYUPIo6iv1UOIIgLiyNtrKsDlGh_JBnCV8mw8",
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBNSIsImlhdCI6MTUxNjIzOTAyMn0.hxYBrb-d4fLYtayhJNxgqvxOwScPW7WYSnL3chYVjsQ",
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBNiIsImlhdCI6MTUxNjIzOTAyMn0.QxAHUpbogFL0wlvwHN9gQqMz14mPM_bvLRkc1aYkp5I",
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBNyIsImlhdCI6MTUxNjIzOTAyMn0.UIyY1De8h0SqU2f06B5DM_5b76hKjTgIm8RkDI2lzJw",
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBOCIsImlhdCI6MTUxNjIzOTAyMn0.IGbDPPURT7Xj-4_FaZsSg7v-f1oYzOkEupLakB2Iy3s",
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBOSIsImlhdCI6MTUxNjIzOTAyMn0.nYWcZryvvEULkr4etvJStFLA861ZRSx4xIBRBJkA-ac",
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMTAiLCJpYXQiOjE1MTYyMzkwMjJ9.cx7YTc-SzDV5-UcQyTOnVVGZBXpmhkbr_H56rr00XNM"
        )

        option.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options)

        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                result.text = "Please Select an Options"
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                result.text = token[position]
                tokenNow = token[position]
            }
        }

        button.setOnClickListener {
//            // Call first for register flutter
            initFlutterEngine()
//            // Call after initFlutterEngine() cause Met hodChannel need flutterEngine
            initMethodChannel()
//            // Call in fragment
            initFlutterFragment()
            Log.d("token!!", tokenNow)
            methodChannel.invokeMethod("open", tokenNow)

            // Call Flutter Page and use withCachedEngine for send data from invokeMethod
            startActivity(
                FlutterActivity.withCachedEngine(App.FLUTTER_ENGINE_ID).build(this)
            )
        }
    }

    private fun initFlutterFragment() {
        val newFlutterFragment: FlutterFragment = FlutterFragment.withCachedEngine(App.FLUTTER_ENGINE_ID).build()
        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().add(R.id.frame, newFlutterFragment).commit()
    }

    private fun initMethodChannel() {
        // Instantiate a MethodChannel send by binaryMessenger. And use CHANNEL to Open Connection.
        methodChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
        // setMethodCallHandler is receive Callback From Flutter.
        methodChannel.setMethodCallHandler { call, _ ->
            when (call.method) {
                "send_back" -> {
                    Log.d("send_back>>>>>", "Data From Flutter is: ${call.arguments}")
                }
            }
        }
    }

    private fun initFlutterEngine() {
        // Instantiate a FlutterEngine.
        flutterEngine = FlutterEngine(this)
        // Start executing Dart code to pre-warm the FlutterEngine.
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )
        // Cache the FlutterEngine to be used by FlutterActivity.
        FlutterEngineCache
            .getInstance()
            .put(App.FLUTTER_ENGINE_ID, flutterEngine)
    }
}