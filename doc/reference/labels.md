# Labels

Tags that categorize elements based and allow showing/hiding them based on user selection (at build time or interactively, depending on output).

Syntax:

```markdown
# `ng` New Game
# `ng1` New Game+
# `ng2` New Game++

# `normal` Normal
# `hard` Hard

# `ng1 & normal` New Game+ (Normal)
# `normal | hard` Ending (Normal or Hard)
```

Labels must be defined in the config:

```hocon
core {
	labels {
		# Difficulty
		normal { name = "Difficulty: Normal", display = "N" }
		hard { name = "Difficulty: Hard", display = "H" }
		
		# New Game Plus
		ng = { name = "New Game" }
		ng1 = { name = "New Game+" }
		ng2 = { name = "New Game++" }
	}
}
```