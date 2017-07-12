package de.liz3.j2h

import org.binaryone.jutils.io.FileUtils
import parser.Parse
import java.io.File

/**
 * Created by liz3 on 12.07.17.
 */
fun main(args:Array<String>) {

    if(args.size < 1) {
        println("No input file specified")
    }

    val inFile = File(args[0])

    val parser = Parse(FileUtils.readFile(inFile))

    println(parser.getParsed())
}