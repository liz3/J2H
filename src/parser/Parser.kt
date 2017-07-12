package parser

import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by liz3 on 12.07.17.
 */
class Parse(val content: String) {


    fun getParsed(): String {

        val root = JSONObject(content)
        var headContent = "";
        for (x in root.keys()) {
            if (x == "body") continue

            if (x == "title") {
                val title = root.getString("title")
                headContent += node("title", HashMap<String, String>(), title)
                continue
            }

            headContent += node(x, root.getJSONArray(x))

        }
        val body = {
            var content = ""

            for (x in root.keys()) {
                if (x == "body") {

                    content = node("body", root.getJSONArray(x))
                    break
                }
            }


            content
        }.invoke()

        val head = node("head", HashMap<String, String>(), headContent)
        return node("html", HashMap<String, String>(), head + body)
    }


    private fun node(name: String, obj: JSONArray): String {

        var content = ""
        try {
            val asOBj = obj.getJSONObject(1)
            for (x in asOBj.keys()) {
                content += node(x, asOBj.getJSONArray(x))
            }
            if (obj.length() == 3) {
                content += obj.getString(2)
            }
        } catch (ignored: Exception) {
            content = obj.get(1) as String
        }

        val map = HashMap<String, String>()

        val attrsObj = obj.getJSONObject(0)

        for (x in attrsObj.keys()) {
            map.put(x, attrsObj.getString(x))
        }

        return node(name, map, content)
    }

    private fun node(tagName: String, attributes: Map<String, String>, content: String): String {
        var parsed = ""

        var rTag = tagName

        if(rTag.contains(" ")) rTag = tagName.split(" ")[0]
        parsed += "<$rTag"

        if (attributes.isNotEmpty()) {
            for ((key, value) in attributes) {
                val rVal = value.replace("\"", "'")
                parsed += " $key=\"$rVal\""

            }
        }
       if (tagName == "img" || tagName == "link") return "$parsed />"



        return "$parsed>$content</$rTag>"
    }
}