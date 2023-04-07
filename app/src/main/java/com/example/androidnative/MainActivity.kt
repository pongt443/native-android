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

        val options = arrayOf("iot-66937060046","iot-66937060048","iot-66937060040","iot-66937060042","iot-66937060045","66659332061", "66659332051", "66937060046", "66937060048", "QA1", "QA2", "QA3", "QA4", "QA5", "QA6", "QA7", "QA8", "QA9", "QA10","QA11", "QA12", "QA13", "QA14", "QA15", "QA16", "QA17", "QA18", "QA19","QA20","QA21", "QA22", "QA23", "QA24", "QA25", "QA26", "QA27", "QA28", "QA29", "QA30")
        val token = arrayOf(
            "eyJhbGciOiJIUzI1NiJ9.eyJtb2JpbGVObyI6IjA5MzcwNjAwNDYiLCJuZXR3b3JrVHlwZSI6IkNQSSIsInRpbWVzdGFtcCI6IjIwMjMtMDQtMDdUMTE6MDY6NDkrMDc6MDAifQ.2KPeNvdWu5M8WiOAsZwguyi-wYxaAJe7B8BT1dn2HL0",
            "eyJhbGciOiJIUzI1NiJ9.eyJtb2JpbGVObyI6IjA5MzcwNjAwNDYiLCJuZXR3b3JrVHlwZSI6IkNQSSIsInRpbWVzdGFtcCI6IjIwMjMtMDQtMDdUMTE6MDc6MzUrMDc6MDAifQ.N3HYY_di5mqZz-TxxWx-q75Po1gH9O7y8cstuhCtcIA",
            "eyJhbGciOiJIUzI1NiJ9.eyJtb2JpbGVObyI6IjA5MzcwNjAwNDYiLCJuZXR3b3JrVHlwZSI6IkNQSSIsInRpbWVzdGFtcCI6IjIwMjMtMDQtMDdUMTE6MDg6MDMrMDc6MDAifQ.zD7W0sImTkhtoBp0bUmcLxSNHfPkRGonVHoVs9Tr9aI",
            "eyJhbGciOiJIUzI1NiJ9.eyJtb2JpbGVObyI6IjA5MzcwNjAwNDYiLCJuZXR3b3JrVHlwZSI6IkNQSSIsInRpbWVzdGFtcCI6IjIwMjMtMDQtMDdUMTE6MDg6MjcrMDc6MDAifQ.dhuY3e4gHx6_OWaFgCUDKOXNrEKGhsPuoOEnzhrPjjE",
            "eyJhbGciOiJIUzI1NiJ9.eyJtb2JpbGVObyI6IjA5MzcwNjAwNDYiLCJuZXR3b3JrVHlwZSI6IkNQSSIsInRpbWVzdGFtcCI6IjIwMjMtMDQtMDdUMTE6MDg6NTArMDc6MDAifQ.eXkxE9QOfOTDB8kw990XTnrytQowStECccXs9xLi1i4",
            "eyJhbGciOiJIUzI1NiJ9.eyJtb2JpbGVObyI6IjA2NTkzMzIwNjEiLCJuZXR3b3JrVHlwZSI6Ik1BU1MiLCJ0aW1lc3RhbXAiOiIyMDIzLTAzLTIzVDE0OjU0OjMyKzA3OjAwIn0.R4eHmEG4oP4pZnKl5bCZPdjDBkhkUzrCBdh1rVZKlJE",
            "eyJhbGciOiJIUzI1NiJ9.eyJtb2JpbGVObyI6IjA2NTkzMzIwNjEiLCJuZXR3b3JrVHlwZSI6Ik1BU1MiLCJ0aW1lc3RhbXAiOiIyMDIzLTAzLTIxVDE0OjIzOjU3KzA3OjAwIn0.HXCVtTlMCrNA18x1zO73jee8z6ADfGkFrBA1recYdBI",
            "eyJhbGciOiJIUzI1NiJ9.eyJtb2JpbGVObyI6IjA2NTkzMzIwNjEiLCJuZXR3b3JrVHlwZSI6Ik1BU1MiLCJ0aW1lc3RhbXAiOiIyMDIzLTAzLTIxVDE0OjI0OjQ5KzA3OjAwIn0.tBmXHX3zHWid_-kP57PgB8rivPvt0z90mrTOHAFfMp4",
            "eyJhbGciOiJIUzI1NiJ9.eyJtb2JpbGVObyI6IjA2NTkzMzIwNjEiLCJuZXR3b3JrVHlwZSI6Ik1BU1MiLCJ0aW1lc3RhbXAiOiIyMDIzLTAzLTIxVDE0OjI1OjM1KzA3OjAwIn0.9FtGXsVL-9KHe8pCCcVYh4XwBIbzE-y0d6pJANA4KLM",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMSIsImlhdCI6MTUxNjIzOTAyMn0.dbYFJPSwNzNj-vPlexSW08gb6yP9q-6LSuFBz746TZE",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMiIsImlhdCI6MTUxNjIzOTAyMn0.wZ8DmfL8ikHSpiJanN9IhzEEDTQ7Q2alkKiyNMgu64k",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMyIsImlhdCI6MTUxNjIzOTAyMn0.MCIzx1QlGbLlYVW5GTn6FrL0gt9gWBhcckC_l1jgweE",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBNCIsImlhdCI6MTUxNjIzOTAyMn0.AYUcoGLYUPIo6iv1UOIIgLiyNtrKsDlGh_JBnCV8mw8",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBNSIsImlhdCI6MTUxNjIzOTAyMn0.hxYBrb-d4fLYtayhJNxgqvxOwScPW7WYSnL3chYVjsQ",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBNiIsImlhdCI6MTUxNjIzOTAyMn0.QxAHUpbogFL0wlvwHN9gQqMz14mPM_bvLRkc1aYkp5I",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBNyIsImlhdCI6MTUxNjIzOTAyMn0.UIyY1De8h0SqU2f06B5DM_5b76hKjTgIm8RkDI2lzJw",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBOCIsImlhdCI6MTUxNjIzOTAyMn0.IGbDPPURT7Xj-4_FaZsSg7v-f1oYzOkEupLakB2Iy3s",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBOSIsImlhdCI6MTUxNjIzOTAyMn0.nYWcZryvvEULkr4etvJStFLA861ZRSx4xIBRBJkA-ac",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMTAiLCJpYXQiOjE1MTYyMzkwMjJ9.cx7YTc-SzDV5-UcQyTOnVVGZBXpmhkbr_H56rr00XNM",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMTEiLCJpYXQiOjE1MTYyMzkwMjJ9.jODcPq62NyJaIiLpHVsY2wBuPb8F5AF3vP564vXq30Y",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMTIiLCJpYXQiOjE1MTYyMzkwMjJ9.2PA3AhJKqidmGGQMMfpyFbZZ2BBCkmJawUyjWwq5h2I",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMTMiLCJpYXQiOjE1MTYyMzkwMjJ9.g4DuYQVhOGJXaZMsEDj_hKr94QqZL-cvNNVYcHkIeqw",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMTQiLCJpYXQiOjE1MTYyMzkwMjJ9.DNOzqkw1ot9Q8n99l3sBrSncoWg_FZMd5aIsKh9gHqM",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMTUiLCJpYXQiOjE1MTYyMzkwMjJ9.nqOlAiXrQHMBgjaa3EOvxB0Dzbb9vqKArmiBKEi-d5U",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMTYiLCJpYXQiOjE1MTYyMzkwMjJ9.EvdbdKx9GUspVF1hiZBPRartt4HygFXRgY5gukzzwVw",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMTciLCJpYXQiOjE1MTYyMzkwMjJ9.6tvRwcdIU_qYtHvB1PLbAnDTpbGNC6gZi7DMG-ZGATc",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMTgiLCJpYXQiOjE1MTYyMzkwMjJ9.7QrzIIWs5lfSABdbUsykQtnb-KCwkPrw81mu0enZ1gI",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMTkiLCJpYXQiOjE1MTYyMzkwMjJ9.H8NADvjBPOpvbQJUMw60eJGIbsUd3Sxv97S38CDzlx0",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMjAiLCJpYXQiOjE1MTYyMzkwMjJ9.4XDcALzD80R7u8p_srYNsZywERsDelXHLwXQzZC5YGw",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMjEiLCJpYXQiOjE1MTYyMzkwMjJ9.AmuUaVHWQdkSfPoKl-gTvI_2hD8vxQG_GjdYW1hDQfw",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMjIiLCJpYXQiOjE1MTYyMzkwMjJ9.XWB-2ugVN9bvWPwj2UeolVCUHLWKYl_3DySD9bGEoGI",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMjMiLCJpYXQiOjE1MTYyMzkwMjJ9.Q-JRv5lU0iwPI31q8sPhZO8hq077x0dTZqu4UqMnjMQ",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMjQiLCJpYXQiOjE1MTYyMzkwMjJ9.5KrkM3ELTQ5B3rPGzJWzhXur0TmFUhVDg9yGzsxDc7E",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMjUiLCJpYXQiOjE1MTYyMzkwMjJ9.57k4YF5C3cSHNc4qk9Bek7kDfWzzWtd1kuJATdCRAMg",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMjYiLCJpYXQiOjE1MTYyMzkwMjJ9.6ek-yMJBDLg4_5k5c-slCtPidlFrcksL86NqeERdiGU",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMjciLCJpYXQiOjE1MTYyMzkwMjJ9.HcDcf8f-Xn2BX11gAEjY7RX3BLhZv-dhAP5YrLZsDFw",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMjgiLCJpYXQiOjE1MTYyMzkwMjJ9.4bBuM9NNM9ql8vWywcoqRyH873fKa27IwMupgNaAin0",
            "5GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMjkiLCJpYXQiOjE1MTYyMzkwMjJ9.BXttYNDdmRvHqNd571z0JytWQjJCU1yz_bHFsqyfbwU",
            "4GeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlFBMzAiLCJpYXQiOjE1MTYyMzkwMjJ9.kZe3awakeMs-qtP4Kanaw_Aim6XqP1Kj6gRbJzwmmZ4"
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
        val newFlutterFragment: FlutterFragment = FlutterFragment.withCachedEngine(App.FLUTTER_ENGINE_ID)
            .shouldAutomaticallyHandleOnBackPressed(true).build()
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