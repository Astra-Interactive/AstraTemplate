package buildlogic

/**
 * Interface which contains basic plugin information
 */
interface ProjectInfo {
    /**
     * ID of project - lowercase
     */
    val id: String

    /**
     * Name of the project
     */
    val name: String

    /**
     * String version of the project
     */
    val version: String

    /**
     * Url of project website or organization
     */
    val url: String

    /**
     * Full description of the project
     */
    val description: String

    /**
     * Authors of the project
     */
    val authors: List<String>

    /**
     * Main class of application
     */
    val main: String
}
