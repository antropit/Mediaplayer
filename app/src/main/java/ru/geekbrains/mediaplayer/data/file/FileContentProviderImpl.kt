package ru.geekbrains.mediaplayer.data.file

import android.os.Environment
import io.reactivex.Flowable
import io.reactivex.internal.operators.flowable.FlowableFromCallable
import ru.geekbrains.mediaplayer.data.MediaSourceData
import ru.geekbrains.mediaplayer.model.MediaType
import java.io.File

class FileContentProviderImpl: FileContentProvider {
    override fun getFiles(): Flowable<List<MediaSourceData>> {
        return FlowableFromCallable {
            val files = mutableListOf<MediaSourceData>()
            if (isExternalStorageReadable()) {
                getPublicStorageDir().listFiles()
                    ?.filter { !it.isDirectory }
                    ?.forEach { file ->
                        val data = MediaSourceData(
                            0,
                            file.name,
                            file.absolutePath,
                            "",
                            MediaType.FILE,
                            false
                        )
                        files.add(data)
                }
            }
            files
        }
    }

    private fun getPublicStorageDir(): File {
        val file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
        if (!file.exists()) {
            file.mkdir()
        }
        return file
    }

    private fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in setOf(
            Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY
        )
    }
}