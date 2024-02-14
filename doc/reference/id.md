# IDs

## Explicit IDs

You can assign an explicit (global) ID to any element that supports it. Syntax:

```markdown
# Chapter 1 {#ch1}

First paragraph. {#p1}
```

Then you can link to those elements by using the ID as the link URL:

```markdown
[First chapter](#ch1)

See [paragrph one](#p1)
```

Explicit IDs have to be unique within a project, as the links have to work across multiple documents.