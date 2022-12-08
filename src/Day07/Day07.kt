import java.util.LinkedList
import java.util.zip.DataFormatException

enum class Command {
    ls, cd
}

data class FileTree(val parent:FileTree?, val name: String, var size:Int = 0, val children:MutableList<FileTree> = mutableListOf())


fun main() {

    fun buildFileTree(input: List<String>): MutableList<FileTree> {
        val list : MutableList<FileTree> = mutableListOf()
        list.add(FileTree(null, "/"))

        var currentDir = list[0];
        input.forEach { s ->
            if (s[0] == '$') {

                val command = Command.valueOf(s.substring(2..3))
                when (command) {
                    Command.ls -> {

                    }
                    Command.cd -> {
                        val dir = s.substring(5)

                        currentDir = when (dir) {
                            "/" -> {
                                list[0];
                            }
                            ".." -> {
                                currentDir.parent ?: list[0]
                            }
                            else -> {
                                val newDir = FileTree(currentDir, dir)
                                currentDir.children.add(newDir)
                                newDir
                            }
                        }
                    }
                }
            }
            else if (s.substring(0..2) == "dir"){


            }
            else {
                var fileline = s.split(' ')
                val size = fileline[0].toInt()
                currentDir.children.add(FileTree(currentDir, fileline[1], size))

                // calculate parent sizes
                var parent: FileTree? = currentDir;
                while (parent != null){
                    parent.size += size;
                    parent = parent.parent;
                }
            }
        }

        return list;
    }

    fun GetSmallDirectories(files :List<FileTree>): MutableList<FileTree> {
        val currentDirectories = mutableListOf<FileTree>();
        files.forEach {
            if(it.children.isNotEmpty()){
                if(it.size <= 100000){
                    currentDirectories.add(it);
                }
                currentDirectories.addAll(GetSmallDirectories(it.children))

            }
        }
        return currentDirectories;
    }

    fun GetBigEnoughDirectories(files :List<FileTree>, size: Int): FileTree {
        var smallestDirectoryWithSpace: FileTree = FileTree(null, "blank", Int.MAX_VALUE);
        files.forEach {
            if(it.children.isNotEmpty()){
                println("${it.name}, ${it.size}")
                if(it.size >= size && it.size <= smallestDirectoryWithSpace.size){
                    smallestDirectoryWithSpace = it;
                }
                val smallestChild = GetBigEnoughDirectories(it.children, size)

                if(smallestChild.size <= smallestDirectoryWithSpace.size){
                    smallestDirectoryWithSpace = smallestChild
                }

            }
        }
        return smallestDirectoryWithSpace;
    }

    fun part1(input: List<String>): Int {

        val fileTree = buildFileTree(input)

        return GetSmallDirectories(fileTree).sumOf { it.size };
    }

    fun part2(input: List<String>): String {
        val fileTree = buildFileTree(input)

        val totalSize = 70000000
        val sizeOfUpdate = 30000000

        val sizeNeeded = sizeOfUpdate - (totalSize - fileTree[0].size)

        val dir = GetBigEnoughDirectories(fileTree, sizeNeeded);
        return "${dir.name} - ${dir.size}"

    }

    fun mapDirs(files :FileTree): List<FileTree> {
        return files.children + files.children.flatMap { mapDirs(it) }
    }

    fun part2_2(input: List<String>): String {
        val fileTree = buildFileTree(input)
        val totalSize = 70000000
        val sizeOfUpdate = 30000000

        val sizeNeeded = sizeOfUpdate - (totalSize - fileTree[0].size)


        val dir = mapDirs(fileTree[0]).filter { it.children.isNotEmpty() && it.size >= sizeNeeded }.sortedBy { it.size }.first()
        return "${dir.name} - ${dir.size}"

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07/Test")
    println(part1(testInput))
    //println(part2(testInput))
    check(part1(testInput) == 95437)
    check(part2(testInput) == "d")
    val input = readInput("Day07/Main")
    println(part1(input))
    println(part2(input))
    println(part2_2(input))
}
