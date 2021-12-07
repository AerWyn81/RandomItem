import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    id("java")
    id("net.minecrell.plugin-yml.bukkit") version "0.5.0"
}

group = "fr.aerwyn81"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.18-R0.1-SNAPSHOT")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }

    jar {
        if (project.hasProperty("cd"))
            archiveFileName.set("RandomItem.jar")
        else
            archiveFileName.set("RandomItem-${archiveVersion.getOrElse("unknown")}.jar")

        destinationDirectory.set(file(System.getenv("outputDir") ?: "$rootDir/build/"))
    }
}

bukkit {
    name = "RandomItem"
    main = "fr.aerwyn81.randomitem.RandomItem"
    authors = listOf("AerWyn81")
    description = "Give a random material to player"
    version = rootProject.version.toString()
    apiVersion = "1.18"
    website = "https://just2craft.fr"

    commands {
        register("randomitem") {
            description = "Plugin command"
            aliases = listOf("ri")
        }
    }

    permissions {
        register("randomitem.admin") {
            description = "Allows access to /randomitem commands"
            default = BukkitPluginDescription.Permission.Default.OP
        }
    }
}