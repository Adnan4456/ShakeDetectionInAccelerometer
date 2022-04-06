package com.example.accelerometersensorchangebackgroundcolor

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.accelerometersensorchangebackgroundcolor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var binding:ActivityMainBinding

    private lateinit var mSensorManager: SensorManager
    private lateinit var mSensor: Sensor
    private lateinit var view:View

    private  var currentX  : Float = 0F
    private  var currentY  : Float = 0F
    private  var currentZ  : Float = 0F

    private  var lastX  : Float = 0F
    private  var lastY  : Float = 0F
    private  var lastZ  : Float = 0F

    private var isNotFirstTime: Boolean = false

    private var xDifference:Float = 0F
    private var yDifference:Float = 0F
    private var zDifference:Float = 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        view = binding.root
        setContentView(view)
        view.setBackgroundColor(Color.WHITE)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE)
            as SensorManager

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){

            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        }else{
            binding.tvSensorX.text = "Sensor not found on this device."
        }
    }

    override fun onResume() {
        super.onResume()

            mSensor.also {
                mSensorManager.registerListener(this , mSensor,
                    SensorManager.SENSOR_DELAY_NORMAL)
            }

    }
    override fun onPause() {
        super.onPause()
            mSensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {

        binding.tvSensorX.text = "X-axis: ${event!!.values[0]}"
        binding.tvSensorY.text = "Y-axis: ${event!!.values[1]}"
        binding.tvSensorZ.text = "Z-axis: ${event!!.values[2]}"

        currentX = event!!.values[0]
        currentY = event!!.values[1]
        currentZ = event!!.values[2]

        if (isNotFirstTime){

            xDifference = Math.abs(lastX - currentX)
            yDifference = Math.abs(lastY - currentY)
            zDifference = Math.abs(lastZ -currentZ)

            if (xDifference >5 && yDifference> 5 ){
                view.setBackgroundColor(Color.CYAN)
            }
        }

        lastX = currentX
        lastY = currentY
        lastZ = currentZ
        isNotFirstTime = true
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}