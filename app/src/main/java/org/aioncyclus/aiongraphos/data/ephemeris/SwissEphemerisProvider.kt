package org.aioncyclus.aiongraphos.data.ephemeris

import android.content.Context
import android.content.res.AssetManager
import swisseph.SwissEph
import java.io.File
import java.io.FileOutputStream


class SwissEphemerisProvider {
    companion object {
        /**
         * Loads and initializes the Swiss Ephemeris with ephemeris files from assets.
         *
         * This method copies the ephemeris data files from the app's assets folder
         * to internal storage, then initializes the Swiss Ephemeris library.
         * The ephemeris files are required for accurate planetary position calculations.
         *
         * @param context The Android context used to access files and assets
         * @return A configured [SwissEph] instance ready for calculations
         *
         * @throws IOException If ephemeris files cannot be copied from assets
         * @throws IllegalArgumentException If ephemeris directory cannot be created
         *
         * @see SwissEph
         */
        fun loadSwissEphemeris(context: Context): SwissEph {
            val ephDir = File(context.filesDir, "ephe")
            copyAssetFolder(context.assets, "ephe", ephDir.path)
            return SwissEph(ephDir.absolutePath)
        }
        /**
         * Recursively copies a folder from the app's assets to the file system.
         *
         * The Swiss Ephemeris requires its ephemeris files to be accessible as
         * regular files on disk. This method preserves the directory structure
         * and copies all files recursively.
         *
         * @param assetManager The Android asset manager
         * @param fromAssetPath The source path within the assets folder
         * @param toPath The destination path in the file system
         *
         * @throws IOException If file copying fails
         */
        fun copyAssetFolder(assetManager: AssetManager, fromAssetPath: String, toPath: String) {
            val files = assetManager.list(fromAssetPath)
            if (files.isNullOrEmpty()) return

            val destDir = File(toPath)
            if (!destDir.exists()) destDir.mkdirs()

            for (file in files) {
                val assetPath = "$fromAssetPath/$file"
                val destFile = File(destDir, file)

                val subFiles = assetManager.list(assetPath)
                if (subFiles.isNullOrEmpty()) {
                    // Its a file
                    assetManager.open(assetPath).use { input ->
                        FileOutputStream(destFile).use { output ->
                            input.copyTo(output)
                        }
                    }
                } else {
                    // It's a directory try again
                    copyAssetFolder(assetManager, assetPath, destFile.path)
                }
            }
        }

    }
}