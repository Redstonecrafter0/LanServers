package net.redstonecraft.lanservers

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.*

interface LanServersPlugin {

    val port: Int
    val motd: String

    companion object {
        private val timer = Timer()
        private val multicastAddress = InetAddress.getByName("224.0.2.60")
        private val multicastPort = 4445
        private lateinit var socket: DatagramSocket
    }

    fun enable() {
        socket = DatagramSocket()
        timer.schedule(object: TimerTask() {
            override fun run() {
                val data = "[MOTD]$motd[/MOTD][AD]$port[/AD]".encodeToByteArray()
                val packet = DatagramPacket(data, data.size, multicastAddress, multicastPort)
                socket.send(packet)
            }
        }, 0, 1500)
    }

    fun disable() {
        timer.cancel()
        socket.close()
    }

}
