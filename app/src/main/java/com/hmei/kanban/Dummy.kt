package com.hmei.kanban

class Dummy {
    fun dummyList():ArrayList<ListItemEntity>{
        val retArr=ArrayList<ListItemEntity>()
        retArr.add(ListItemEntity("MyTodo01", "todo"))
        retArr.add(ListItemEntity("MyTodo02", "todo"))
        retArr.add(ListItemEntity("MyTodo03", "todo"))
        retArr.add(ListItemEntity("MyTodo04", "todo"))
        retArr.add(ListItemEntity("MyTodo05", "todo"))
        retArr.add(ListItemEntity("MyTodo06", "todo"))
        retArr.add(ListItemEntity("Prog01", "in_progress"))
        retArr.add(ListItemEntity("Prog02", "in_progress"))
        retArr.add(ListItemEntity("Prog03", "in_progress"))
        retArr.add(ListItemEntity("Prog04", "in_progress"))
        retArr.add(ListItemEntity("Prog05", "in_progress"))
        retArr.add(ListItemEntity("Done01", "done"))
        retArr.add(ListItemEntity("Done02", "done"))
        retArr.add(ListItemEntity("Done03", "done"))
        retArr.add(ListItemEntity("Done04", "done"))
        return retArr
    }

    fun dummyListTodo():ArrayList<ListItemEntity> {
        val retArr=ArrayList<ListItemEntity>()
        dummyList().forEach {
            if (it.category=="todo"){
                retArr.add(it)
            }
        }
        return retArr
    }

    fun dummyListInProgress():ArrayList<ListItemEntity> {
        val retArr=ArrayList<ListItemEntity>()
        dummyList().forEach {
            if (it.category=="in_progress"){
                retArr.add(it)
            }
        }
        return retArr
    }

    fun dummyListDone():ArrayList<ListItemEntity> {
        val retArr=ArrayList<ListItemEntity>()
        dummyList().forEach {
            if (it.category=="done"){
                retArr.add(it)
            }
        }
        return retArr
    }

    fun setGlobalLists(){
        Global.todoList=dummyListTodo()
        Global.inProgressList=dummyListInProgress()
        Global.doneList=dummyListDone()
        dummyList().forEach {
            Global.fullList.add(it)
        }
    }
}