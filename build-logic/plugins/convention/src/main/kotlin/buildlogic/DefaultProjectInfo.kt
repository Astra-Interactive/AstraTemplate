package buildlogic

data class DefaultProjectInfo(
    override val id: String,
    override val name: String,
    override val version: String,
    override val url: String,
    override val description: String,
    override val authors: List<String>,
    override val main: String
) : ProjectInfo
