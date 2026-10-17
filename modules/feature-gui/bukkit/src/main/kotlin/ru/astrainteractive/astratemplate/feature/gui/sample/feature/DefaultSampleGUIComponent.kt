package ru.astrainteractive.astratemplate.feature.gui.sample.feature

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.bukkit.ChatColor
import org.bukkit.event.inventory.ClickType
import ru.astrainteractive.astralibs.coroutines.withTimings
import ru.astrainteractive.astratemplate.data.dao.user.api.UserRepository
import ru.astrainteractive.astratemplate.data.dao.user.model.User
import ru.astrainteractive.astratemplate.feature.gui.api.ItemStackSpigotAPI
import ru.astrainteractive.astratemplate.feature.gui.domain.GetRandomColorUseCase
import ru.astrainteractive.astratemplate.feature.gui.domain.SetDisplayNameUseCase
import ru.astrainteractive.klibs.mikro.core.coroutines.CoroutineFeature
import ru.astrainteractive.klibs.mikro.core.logging.JUtiltLogger
import ru.astrainteractive.klibs.mikro.core.logging.Logger
import kotlin.random.Random

/**
 * MVVM/MVI technique
 */
internal class DefaultSampleGUIComponent(
    private val userRepository: UserRepository,
    private val itemStackSpigotAPi: ItemStackSpigotAPI,
    private val getRandomColorUseCase: GetRandomColorUseCase,
    private val setDisplayNameUseCase: SetDisplayNameUseCase
) : CoroutineFeature by CoroutineFeature.IO.withTimings(),
    SampleGuiComponent,
    Logger by JUtiltLogger("AstraTemplate-DefaultSampleGUIComponent") {

    override val model = MutableStateFlow<SampleGuiComponent.Model>(SampleGuiComponent.Model.Loading)

    override val randomColor: ChatColor
        get() = getRandomColorUseCase.invoke().color

    @Suppress("MagicNumber")
    private fun getRandomUser(): User {
        return User(
            id = -1,
            discordId = "id${Random.nextInt(20000)}",
            minecraftUUID = "mine${Random.nextInt(5000)}"
        )
    }

    override fun onModeChange() {
        launch(Dispatchers.IO) {
            when (model.value) {
                SampleGuiComponent.Model.Loading -> return@launch
                is SampleGuiComponent.Model.Items -> loadUsersModel()
                is SampleGuiComponent.Model.Users -> loadItemsModel()
            }
        }
    }

    override fun onItemClicked(slot: Int, clickType: ClickType) {
        when (val state = model.value) {
            SampleGuiComponent.Model.Loading -> Unit
            is SampleGuiComponent.Model.Items -> {
                onItemStackClicked(slot)
            }

            is SampleGuiComponent.Model.Users -> {
                onPlayerHeadClicked(slot, clickType)
            }
        }
    }

    override fun onAddUserClicked() {
        launch(Dispatchers.IO) {
            userRepository.insertUser(getRandomUser())
            loadUsersModel()
        }
    }

    private fun onPlayerHeadClicked(slot: Int, clickType: ClickType) {
        val state = model.value as? SampleGuiComponent.Model.Users ?: return
        val users = state.users
        val user = users.getOrNull(slot) ?: return
        launch(Dispatchers.IO) {
            when (clickType) {
                ClickType.MIDDLE -> userRepository.updateUser(user)
                ClickType.LEFT -> userRepository.deleteUser(user)
                else -> userRepository.insertRating(user)
            }
            loadUsersModel()
        }
    }

    private fun onItemStackClicked(slot: Int) {
        val state = model.value as? SampleGuiComponent.Model.Items ?: return

        val input = SetDisplayNameUseCase.Input(
            items = state.items,
            index = slot
        )

        model.update {
            state.copy(items = setDisplayNameUseCase.invoke(input).items)
        }
    }

    private suspend fun loadItemsModel() {
        model.update { SampleGuiComponent.Model.Items(itemStackSpigotAPi.randomItemStackList()) }
    }

    private suspend fun loadUsersModel() {
        model.update { SampleGuiComponent.Model.Users(userRepository.getAllUsers()) }
    }

    override fun onUiCreated() {
        launch(Dispatchers.IO) {
            @Suppress("MagicNumber")
            delay(1000)
            loadItemsModel()
        }
    }
}
