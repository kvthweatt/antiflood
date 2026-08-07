# AntiFlood

A Paper plugin for Minecraft servers that removes flood blocks (water/lava) above a specified Y level across all loaded chunks. Built to counter griefing via redstone-powered flood machines.

## Features

- Remove water, lava, or both above any Y level
- Processes chunks asynchronously across ticks to prevent lag
- Cancellable mid-operation
- Reports total blocks removed on completion

## Commands

| Command | Description |
|--------|-------------|
| `/antiflood <y> [water\|lava\|both]` | Remove flood blocks above the given Y level |
| `/antifloodstop` | Stop an ongoing antiflood operation |

**Examples:**

/antiflood 100
/antiflood 100 water
/antiflood 100 lava
/antifloodstop


## Permissions

| Permission | Description | Default |
|-----------|-------------|---------|
| `antiflood.use` | Allows use of all antiflood commands | OP |

## Installation

1. Download the latest `.jar` from [Releases](../../releases)
2. Drop it into your server's `plugins/` folder
3. Restart the server
4. No configuration needed — run `/antiflood` when you need it

## Building from Source

Requires Java 21 and Maven.

```bash
git clone https://github.com/yourusername/AntiFlood.git
cd AntiFlood
mvn package
```

Output jar will be in `target/`.

## Compatibility

- **Platform:** Paper
- **Minecraft:** 1.21.x
- **Java:** 21+

## Planned

- Fabric / Forge port
- Per-world targeting
- Configurable chunk processing speed
- Auto-detection of active flood machines (unknown if possible, but idea is there)

## Contributing

PRs welcome. If you're porting to Fabric, Forge, or another platform, open an issue first so efforts aren't duplicated.

## License

MIT
