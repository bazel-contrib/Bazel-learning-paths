def _impl(ctx):
    print(ctx.attr.content)

message = rule(
    implementation = _impl,
    attrs = {
        "content": attr.string(),
    },
)
