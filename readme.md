# SkriptYml

A powerful Skript addon for easily managing YAML files directly in your Skript scripts.

## Features

- ✅ Read, write, and delete data from YAML files
- ✅ Support for nested keys and complex data structures
- ✅ Automatic file creation and directory generation
- ✅ List and object storage support
- ✅ File existence checking and key listing
- ✅ High-performance with file caching

## Requirements

- Skript 2.7.0+
- Minecraft 1.21+

## Installation

1. Download the latest release from [GitHub Releases](https://github.com/beklauter/skript-yml/releases)
2. Place the JAR file in your server's `plugins` folder
3. Restart your server
4. Start using YAML operations in your Skript scripts!

## Basic Syntax

### Reading Data

```
yaml value "key" from file "path/to/file.yml"
```

### Setting Data

```
set yaml value "key" in file "path/to/file.yml" to "value"
```

### Deleting Data

```
delete yaml value "key" from file "path/to/file.yml"
```

## Advanced Usage

### Working with Nested Keys

```
# Setting nested values
set yaml value "players.%player's uuid%.name" in file "plugins/data.yml" to player's name
set yaml value "players.%player's uuid%.stats.kills" in file "plugins/data.yml" to 0

# Reading nested values
set {_name} to yaml value "players.%player's uuid%.name" from file "plugins/data.yml"
```

### Working with Lists

```
# Storing lists
set {_players::*} to ("Player1", "Player2", "Player3")
set yaml value "whitelist" in file "plugins/server.yml" to {_players::*}

# Reading lists
set {_storedPlayers::*} to yaml value "whitelist" from file "plugins/server.yml"
```

### File Operations

```
# Check if a file exists
if yaml file "plugins/config.yml" exists:
    send "Config file exists!"

# Get all keys from a section
set {_allKeys::*} to yaml keys "players" from file "plugins/data.yml"

# Force save a file
save yaml file "plugins/config.yml"
```

## Example Script

```
on join:
    # Load or initialize player data
    set {_uuid} to player's uuid
    set {_file} to "plugins/playerstats/stats.yml"

    # Check if player exists in the file
    set {_joinCount} to yaml value "players.%{_uuid}%.joins" from file {_file}

    if {_joinCount} is not set:
        # New player
        set yaml value "players.%{_uuid}%.name" in file {_file} to player's name
        set yaml value "players.%{_uuid}%.joins" in file {_file} to 1
        set yaml value "players.%{_uuid}%.firstJoin" in file {_file} to now
    else:
        # Existing player
        set {_newCount} to {_joinCount} + 1
        set yaml value "players.%{_uuid}%.joins" in file {_file} to {_newCount}
        set yaml value "players.%{_uuid}%.lastJoin" in file {_file} to now

    # Send welcome message
    set {_name} to yaml value "players.%{_uuid}%.name" from file {_file}
    set {_joins} to yaml value "players.%{_uuid}%.joins" from file {_file}
    send "Welcome %{_name}%! This is your %{_joins}% time joining."
```

## Best Practices

- Use organized paths with namespaces (e.g., `player.stats.kills`)
- Save regularly after making multiple changes
- Check if values exist before using them
- For frequently accessed data, use variables instead of reading from file repeatedly
- Implement regular backups for important YAML files

## Documentation

Full documentation is available at: [https://vortex-esports.xyz/skriptYml/SkriptYml](https://vortex-esports.xyz/skriptYml/SkriptYml)

## For Developers

If you want to use this addon's functionality in your Java code:

```java
// Import the YamlManager
import de.beklauter.skriptYml.utils.YamlManager;

// Read a value
Object value = YamlManager.getValue("plugins/config.yml", "some.key");

// Set a value
YamlManager.setValue("plugins/config.yml", "some.key", "new value");

// Delete a value
YamlManager.removeValue("plugins/config.yml", "some.key");
```

## License

This project is licensed under the MIT License.

## Author

Created by kbl/beklauter