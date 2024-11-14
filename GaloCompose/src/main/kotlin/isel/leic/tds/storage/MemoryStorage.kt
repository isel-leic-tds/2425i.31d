package isel.leic.tds.storage

class MemoryStorage<Key, Data: Any>: Storage<Key, Data> {
    val map: MutableMap<Key,Data> = mutableMapOf()

    override fun create(key: Key, data: Data) {
        if (key in map) error("entry $key already exists")
        map[key] = data
    }

    override fun read(key: Key): Data? = map[key]

    override fun update(key: Key, data: Data) {
        if (key !in map) error("entry $key not exists")
        map[key] = data
    }

    override fun delete(key: Key) {
        check(map.remove(key)!=null) { "entry $key not found" }
    }
}