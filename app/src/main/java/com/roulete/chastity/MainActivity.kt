package com.roulete.chastity

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.Image
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyRow
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.UUID
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.random.Random

private val DORO_FALLBACK_MODELS = listOf(
    "gemini-3.6-flash",
    "gemini-3.5-flash",
    "gemini-3.5-flash-lite",
    "gemini-3.1-flash",
    "gemini-3.1-flash-lite",
)

private fun isRetryableHttpError(responseCode: Int, body: String): Boolean {
    if (responseCode in 500..599) return true
    if (responseCode != 429) return false
    return true
}

private class GeminiHttpException(
    val responseCode: Int,
    val responseBody: String,
) : java.io.IOException("HTTP $responseCode: $responseBody")

private fun callGemini(apiKey: String, model: String, requestBody: JSONObject): String {
    val url = URL("https://generativelanguage.googleapis.com/v1beta/models/$model:generateContent?key=$apiKey")
    val connection = (url.openConnection() as HttpURLConnection).apply {
        requestMethod = "POST"
        connectTimeout = 20_000
        readTimeout = 45_000
        doOutput = true
        setRequestProperty("Content-Type", "application/json")
    }
    return try {
        connection.outputStream.use { it.write(requestBody.toString().toByteArray(Charsets.UTF_8)) }
        val responseCode = connection.responseCode
        if (responseCode in 200..299) {
            connection.inputStream.bufferedReader().use { it.readText() }
        } else {
            val error = connection.errorStream?.bufferedReader()?.use { it.readText() }.orEmpty()
            throw GeminiHttpException(responseCode, error)
        }
    } finally {
        connection.disconnect()
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RouleteApp()
        }
    }
}

private data class DailyTask(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val rewardTokens: Int = 0,
    val completed: Boolean = false,
    val rewardClaimedDay: String? = null,
    val validationStatus: MissionValidationStatus = MissionValidationStatus.Draft,
    val difficulty: MissionDifficulty? = null,
    val validationReason: String? = null,
    val missionSource: MissionSource = MissionSource.Custom,
)

private data class HistoryEntry(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val detail: String,
    val minutesDelta: Int,
    val kind: String,
    val rarity: String? = null,
    val timestampMillis: Long = System.currentTimeMillis(),
)

private data class ProofCheck(
    val code: String,
    val frozenRemainingMillis: Long,
    val startedAtMillis: Long = System.currentTimeMillis(),
    val attempts: Int = 0,
    val lastMessage: String? = null,
)

private data class ProofVerdict(
    val passed: Boolean,
    val confidence: Double,
    val reason: String,
)

private data class ProofLog(
    val id: String = UUID.randomUUID().toString(),
    val code: String,
    val passed: Boolean,
    val confidence: Double,
    val reason: String,
    val penaltyMinutes: Int = 0,
    val timestampMillis: Long = System.currentTimeMillis(),
)

private enum class MissionValidationStatus {
    Draft,
    Validated,
    Rejected,
    Failed,
}

private enum class MissionSource {
    Template,
    Custom,
    Daily,
}

private val DAILY_AUTO_MISSIONS: List<DailyTask> = listOf(
    DailyTask(
        id = "daily_health_check",
        title = "Check cage and ball for any health issues or discomfort",
        validationStatus = MissionValidationStatus.Validated,
        difficulty = MissionDifficulty.Easy,
        rewardTokens = MissionDifficulty.Easy.tokens,
        missionSource = MissionSource.Daily,
    ),
    DailyTask(
        id = "daily_lock_secure",
        title = "Confirm the lock is still secure and the key is nowhere near you",
        validationStatus = MissionValidationStatus.Validated,
        difficulty = MissionDifficulty.Easy,
        rewardTokens = MissionDifficulty.Easy.tokens,
        missionSource = MissionSource.Daily,
    ),
    DailyTask(
        id = "daily_hygiene",
        title = "Take a bath and clean your clitty properly",
        validationStatus = MissionValidationStatus.Validated,
        difficulty = MissionDifficulty.Medium,
        rewardTokens = MissionDifficulty.Medium.tokens,
        missionSource = MissionSource.Daily,
    ),
    DailyTask(
        id = "daily_reflection",
        title = "Spend 5 minutes staring at your flat cage and say out loud that you don't deserve release",
        validationStatus = MissionValidationStatus.Validated,
        difficulty = MissionDifficulty.Medium,
        rewardTokens = MissionDifficulty.Medium.tokens,
        missionSource = MissionSource.Daily,
    ),
)

private enum class MissionDifficulty(val label: String, val tokens: Int) {
    Easy("Easy", 5),
    Medium("Medium", 7),
    Hard("Hard", 10),
    Hardcore("Hardcore", 20),
}

private data class MissionValidationResult(
    val id: String,
    val valid: Boolean,
    val difficulty: MissionDifficulty?,
    val reason: String,
)

private data class AppState(
    val tasks: List<DailyTask> = emptyList(),
    val lockedUntilMillis: Long = System.currentTimeMillis(),
    val betaTokens: Int = 0,
    val history: List<HistoryEntry> = emptyList(),
    val discreetMode: Boolean = false,
    val lastResetDay: String = todayKey(),
    val onboardingComplete: Boolean = false,
    val tutorialComplete: Boolean = false,
    val strictMode: Boolean = false,
    val geminiApiKey: String = "",
    val proofChecksEnabled: Boolean = false,
    val proofCheck: ProofCheck? = null,
    val proofChancePercentPerHour: Int = 8,
    val proofQuietStartHour: Int = 0,
    val proofQuietEndHour: Int = 0,
    val proofFailurePenaltyMinutes: Int = 0,
    val proofHistory: List<ProofLog> = emptyList(),
    val selectedCase: CaseType = CaseType.Denial,
)

private enum class Rarity {
    MilSpec,
    Restricted,
    Classified,
    Covert,
    Gold,
}

private enum class CaseType(val title: String, val subtitle: String) {
    Pity("Pity Case", "Soft odds. Still embarrassing."),
    Denial("Denial Case", "The default little trap."),
    Extinction("Extinction Case", "For bad decisions only."),
}

private enum class GamblingMachine(val title: String, val subtitle: String) {
    Case("Case", "Classic reel sentence box."),
    Roulette("Roulette", "Pick number and color."),
    Tower("Cage Tower", "Climb, cash out, or collapse."),
    Drop("Lock Drop", "Ball drop into sentence slots."),
}

private enum class DropRisk(val title: String) {
    Low("Low"),
    Mid("Mid"),
    High("High"),
}

private enum class RouletteBetColor(val label: String) {
    Red("Red"),
    Black("Black"),
    Green("Green"),
}

private data class GamblingResult(
    val title: String,
    val detail: String,
    val minutesDelta: Int,
    val machine: String,
)

private const val CaseCostTokens = 3
private const val RouletteCostTokens = 5
private const val TowerCostTokens = 6
private const val DropCostTokens = 5

private data class Prize(
    val label: String,
    val description: String,
    val minutesDelta: Int,
    val rarity: Rarity,
    val weight: Int,
)

private enum class Screen(val label: String, val icon: ImageVector) {
    Dashboard("Home", Icons.Filled.Home),
    Tasks("Task", Icons.Filled.TaskAlt),
    Gacha("Gamble", Icons.Filled.Casino),
    History("History", Icons.Filled.History),
    Shop("Shop", Icons.Filled.ShoppingBag),
    Settings("Settings", Icons.Filled.Settings),
}

private val PrimaryScreens = listOf(Screen.Dashboard, Screen.Tasks, Screen.Gacha)
private val DrawerScreens = listOf(Screen.History, Screen.Shop, Screen.Settings)

private enum class TutorialTarget {
    Countdown,
    MissionActions,
    GamblingChoices,
    Menu,
    GeminiSetup,
}

private data class TutorialPage(
    val title: String,
    val body: String,
    val screen: Screen,
    val target: TutorialTarget,
    val actionLabel: String? = null,
    val actionUrl: String? = null,
)

private val tutorialPages = listOf(
    TutorialPage(
        title = "Your sentence",
        body = "This is the live lock countdown. Time is stored as an end date, so closing the app does not stop it.",
        screen = Screen.Dashboard,
        target = TutorialTarget.Countdown,
    ),
    TutorialPage(
        title = "Build today's missions",
        body = "Add missions here, then Validate sends only new or edited ones to Gemini. Approved missions pay BetaTokens when completed.",
        screen = Screen.Tasks,
        target = TutorialTarget.MissionActions,
    ),
    TutorialPage(
        title = "Spend your BetaTokens",
        body = "Pick a machine from this grid. Every play costs tokens and its result can add or remove lock time.",
        screen = Screen.Gacha,
        target = TutorialTarget.GamblingChoices,
    ),
    TutorialPage(
        title = "Everything else lives here",
        body = "Open the menu for History, Shop, Settings, and the button to replay this tour.",
        screen = Screen.Dashboard,
        target = TutorialTarget.Menu,
    ),
    TutorialPage(
        title = "Connect Gemini",
        body = "Paste a Gemini API key here. It stays in this app's local storage and powers mission validation and proof checks.",
        screen = Screen.Settings,
        target = TutorialTarget.GeminiSetup,
        actionLabel = "Open Google AI Studio",
        actionUrl = "https://aistudio.google.com/apikey",
    ),
)

private fun Modifier.tutorialTarget(
    target: TutorialTarget,
    onBounds: (TutorialTarget, Rect) -> Unit,
): Modifier = onGloballyPositioned { coordinates ->
    onBounds(target, coordinates.boundsInRoot())
}

private val prizeTables = mapOf(
    CaseType.Pity to listOf(
        Prize("Tiny Lecture for Tiny Dick", "+30 minutes. A soft warning for a tiny problem.", 30, Rarity.MilSpec, 1350),
        Prize("Cute Little Delay, Still Useless", "+1 hour. Even your luck is unimpressive.", 1.hoursMinutes, Rarity.MilSpec, 1250),
        Prize("Training Wheels for a Failure", "+2 hours. You still need help staying locked.", 2.hoursMinutes, Rarity.MilSpec, 1050),
        Prize("Pity Drip, Don’t Get Excited", "-30 minutes. Take the crumb and stop pretending it means freedom.", -30, Rarity.MilSpec, 850),
        Prize("Mercy Crumb for a Locked Loser", "-1 hour. A consolation prize for losing properly.", -1.hoursMinutes, Rarity.MilSpec, 560),
        Prize("Soft Denial, Stay Desperate", "+4 hours. Hope is still not getting you out.", 4.hoursMinutes, Rarity.Restricted, 520),
        Prize("Good Boy Delay (you’re not one)", "+6 hours. Wear the label; you haven’t earned the title.", 6.hoursMinutes, Rarity.Restricted, 390),
        Prize("Leash Loosened… Barely", "-3 hours. The leash moved; you did not.", -3.hoursMinutes, Rarity.Restricted, 220),
        Prize("One-Day Reminder You’re Owned", "+1 day. Let the calendar reinforce the ownership.", 1.daysMinutes, Rarity.Classified, 120),
        Prize("Annoying Mercy, Don’t Thank Me", "-6 hours. Accept the discount without getting grateful.", -6.hoursMinutes, Rarity.Classified, 85),
        Prize("Pity Coupon for a Cuck", "-1 day. Spend your little coupon and stay embarrassed.", -1.daysMinutes, Rarity.Covert, 20),
        Prize("Soft Reset Fantasy, Still Caged", "-2 days. Imagine release while the cage stays shut.", -2.daysMinutes, Rarity.Gold, 3),
    ),
    CaseType.Denial to listOf(
        Prize("Still Tiny, Always Will Be", "+2 hours. A little attempt, same little result.", 2.hoursMinutes, Rarity.MilSpec, 1300),
        Prize("Denied Again, Cry About It", "+4 hours. The lock heard you hope and added time.", 4.hoursMinutes, Rarity.MilSpec, 1300),
        Prize("Clitty Timeout, Hands Off", "+6 hours. Hands stay away; your frustration can stay close.", 6.hoursMinutes, Rarity.MilSpec, 1200),
        Prize("Extinction Day, No Release", "+1 day. Put the fantasy back in the drawer.", 1.daysMinutes, Rarity.MilSpec, 1050),
        Prize("Hands Homework, Edge and Fail", "Extra 100 Edges. Work harder at failing.", 0, Rarity.MilSpec, 950),
        Prize("Pity Drip, Then Nothing", "-30 minutes. Enjoy the drip; nothing else is coming.", -30, Rarity.MilSpec, 650),
        Prize("Micro Mercy, Instantly Regretted", "-1 hour. One hour off, immediately regretted. Don’t smile.", -1.hoursMinutes, Rarity.MilSpec, 350),
        Prize("Bedtime Caged, Dream of Cock", "+12 hours. Sleep locked and dream about what you cannot touch.", 12.hoursMinutes, Rarity.Restricted, 420),
        Prize("Two-Day Toy, Not Yours", "+2 days. The toy stays out of reach; so does release.", 2.daysMinutes, Rarity.Restricted, 360),
        Prize("Denial Debt, Keep Paying", "Double current sentence, capped at +3 days. Interest is due immediately.", Int.MIN_VALUE, Rarity.Restricted, 300),
        Prize("Cage Vacation, No Exit", "+3 days. Check in, settle down, and forget the exit.", 3.daysMinutes, Rarity.Restricted, 260),
        Prize("Leash Loosened… Then Tightened", "-3 hours. A brief slip, followed by a tighter pull.", -3.hoursMinutes, Rarity.Restricted, 180),
        Prize("Calendar Tax, Another Week", "+5 days. The calendar takes another week from you.", 5.daysMinutes, Rarity.Classified, 95),
        Prize("A Week Beneath Me", "+7 days. Spend another week exactly where you belong.", 7.daysMinutes, Rarity.Classified, 80),
        Prize("Look, Drip, Suffer", "No touch and daily check-ins for 2 weeks. Watch, report, and suffer.", 0, Rarity.Classified, 70),
        Prize("Mercy Leak, Wasted", "-12 hours. Even your lucky leak feels wasted.", -12.hoursMinutes, Rarity.Classified, 40),
        Prize("Extinction Spiral, Keep Spinning", "+14 to +21 days randomized. The wheel keeps turning until hope gives up.", Int.MAX_VALUE, Rarity.Covert, 26),
        Prize("Sentence Breeder, Stay Locked", "Current sentence is completely doubled. Your sentence reproduces; you remain locked.", Int.MIN_VALUE + 1, Rarity.Covert, 20),
        Prize("Pity Coupon, Expired", "-1 day. The coupon is expired; the tiny mercy is still insulting.", -1.daysMinutes, Rarity.Covert, 12),
        Prize("Total Blackout, No Hope", "+120 Days. Four months of darkness, with no hope attached.", 120.daysMinutes, Rarity.Gold, 25),
        Prize("Thirty-Minute Accident, Then Back In", "30 Minutes of Freedom. Enjoy the accident, then get back in.", -30, Rarity.Gold, 1),
    ),
    CaseType.Extinction to listOf(
        Prize("No Mercy Warmup", "+6 hours. This is the gentle warmup. It gets worse.", 6.hoursMinutes, Rarity.MilSpec, 1200),
        Prize("Twelve-Hour Reminder You’re Nothing", "+12 hours. Twelve hours to remember how little your hope matters.", 12.hoursMinutes, Rarity.MilSpec, 1150),
        Prize("One-Day Ownership, Permanent Mindset", "+1 day. One day of ownership to make the mindset permanent.", 1.daysMinutes, Rarity.MilSpec, 1000),
        Prize("Two-Day Correction", "+2 days. Two days should correct that optimism.", 2.daysMinutes, Rarity.MilSpec, 850),
        Prize("Tiny Mercy Error, Fixed", "-30 minutes. A tiny error in mercy, corrected by your continued existence.", -30, Rarity.MilSpec, 420),
        Prize("Weekend Removed", "+3 days. Your weekend has been removed from the schedule.", 3.daysMinutes, Rarity.Restricted, 520),
        Prize("Five-Day Lesson in Failure", "+5 days. Five days to study the same lesson: you fail.", 5.daysMinutes, Rarity.Restricted, 390),
        Prize("Sentence Debt, Compound Interest", "Double current sentence, capped at +3 days. The debt compounds because you do.", Int.MIN_VALUE, Rarity.Restricted, 300),
        Prize("Mercy Leak, Clean It Up", "-1 hour. Clean up the mercy leak and stop calling it luck.", -1.hoursMinutes, Rarity.Restricted, 130),
        Prize("A Week Beneath Me", "+7 days. A full week beneath me, exactly as scheduled.", 7.daysMinutes, Rarity.Classified, 160),
        Prize("Two-Week Drain", "+14 days. Two weeks drained from your life and poured into the lock.", 14.daysMinutes, Rarity.Classified, 90),
        Prize("Barely Spared, Still Owned", "-3 hours. Barely spared, never released, still owned.", -3.hoursMinutes, Rarity.Classified, 35),
        Prize("Extinction Spiral", "+14 to +21 days randomized. The spiral has no concern for your plans.", Int.MAX_VALUE, Rarity.Covert, 45),
        Prize("Sentence Breeder", "Current sentence is completely doubled. The sentence grows; your freedom does not.", Int.MIN_VALUE + 1, Rarity.Covert, 36),
        Prize("Pity Coupon (worthless)", "-1 day. Worthless luck, briefly less sentence, no dignity returned.", -1.daysMinutes, Rarity.Covert, 10),
        Prize("Total Blackout", "+120 Days. Four months erased from anything resembling freedom.", 120.daysMinutes, Rarity.Gold, 35),
        Prize("Thirty-Minute Accident", "30 Minutes of Freedom. A brief administrative error before the cage closes again.", -30, Rarity.Gold, 1),
    ),
)

private val Int.hours: Long get() = this * 60L * 60L * 1_000L
private val Int.minutesMillis: Long get() = this * 60L * 1_000L
private val Int.hoursMinutes: Int get() = this * 60
private val Int.daysMinutes: Int get() = this * 24 * 60
private const val WinningPrizeIndex = 52

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun RouleteApp() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val reduceMotion = (context.resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK) == 0
    var state by remember { mutableStateOf(AppStore.load(context).withDailyReset()) }
    var selected by remember { mutableStateOf(Screen.Dashboard) }
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var opening by remember { mutableStateOf(false) }
    var reel by remember { mutableStateOf(generateReel(null, state.casePrizes())) }
    var pendingPrize by remember { mutableStateOf<Prize?>(null) }
    var spinToken by remember { mutableLongStateOf(0L) }
    var skipToken by remember { mutableLongStateOf(0L) }
    var finishJob by remember { mutableStateOf<Job?>(null) }
    var proofBusy by remember { mutableStateOf(false) }
    var proofStatus by remember { mutableStateOf<String?>(null) }
    var missionValidationBusy by remember { mutableStateOf(false) }
    var missionValidationStatus by remember { mutableStateOf<String?>(null) }
    var pendingGambleResult by remember { mutableStateOf<GamblingResult?>(null) }
    var doubleAttempt by remember { mutableStateOf(0) }
    var tutorialStep by remember { mutableStateOf(0) }
    val tutorialBounds = remember { mutableStateMapOf<TutorialTarget, Rect>() }
    val isLocked = state.proofCheck != null || state.lockedUntilMillis > System.currentTimeMillis()
    val strictActive = state.strictMode && isLocked
    val rejectedMissionBlock = state.tasks.any { it.validationStatus == MissionValidationStatus.Rejected }

    fun startProofCheck() {
        val now = System.currentTimeMillis()
        val remaining = max(0L, state.lockedUntilMillis - now)
        if (remaining <= 0L || state.proofCheck != null) return
        state = state.copy(
            proofCheck = ProofCheck(
                code = Random.nextInt(0, 100_000).toString().padStart(5, '0'),
                frozenRemainingMillis = remaining,
            ),
            history = listOf(
                HistoryEntry(
                    title = "Proof check started",
                    detail = "Timer frozen until verification passes.",
                    minutesDelta = 0,
                    kind = "proof",
                )
            ) + state.history,
        )
        proofStatus = null
    }

    fun applyProofVerdict(verdict: ProofVerdict) {
        val proof = state.proofCheck ?: return
        val penaltyMinutes = if (verdict.passed) 0 else state.proofFailurePenaltyMinutes
        val proofLog = ProofLog(
            code = proof.code,
            passed = verdict.passed,
            confidence = verdict.confidence,
            reason = verdict.reason,
            penaltyMinutes = penaltyMinutes,
        )
        val entry = HistoryEntry(
            title = if (verdict.passed) "Proof check passed" else "Proof check failed",
            detail = "Code ${proof.code}. ${(verdict.confidence * 100).roundToInt()}% confidence. ${verdict.reason}",
            minutesDelta = penaltyMinutes,
            kind = "proof",
        )
        state = if (verdict.passed) {
            state.copy(
                lockedUntilMillis = System.currentTimeMillis() + proof.frozenRemainingMillis,
                proofCheck = null,
                proofHistory = listOf(proofLog) + state.proofHistory,
                history = listOf(entry) + state.history,
            )
        } else {
            state.copy(
                proofCheck = proof.copy(
                    frozenRemainingMillis = proof.frozenRemainingMillis + penaltyMinutes.minutesMillis,
                    attempts = proof.attempts + 1,
                    lastMessage = verdict.reason,
                ),
                proofHistory = listOf(proofLog) + state.proofHistory,
                history = listOf(entry) + state.history,
            )
        }
        proofStatus = if (verdict.passed) {
            "Proof passed. Timer resumed."
        } else {
            "Proof failed: ${verdict.reason}"
        }
    }

    fun finishOpeningNow() {
        val prize = pendingPrize
        if (opening && prize != null) {
            finishJob?.cancel()
            state = state.applyPrize(prize)
            skipToken += 1
            opening = false
        }
    }

    fun acceptGambleResult(result: GamblingResult) {
        state = state.applyGamblingResult(result)
        pendingGambleResult = null
        doubleAttempt = 0
    }

    fun offerGambleResult(result: GamblingResult) {
        pendingGambleResult = result
        doubleAttempt = 0
    }

    fun doubleGambleResult(result: GamblingResult) {
        val chance = doubleOdds(doubleAttempt)
        val won = Random.nextInt(100) < chance
        if (won) {
            pendingGambleResult = improveGamblingResult(result, doubleAttempt + 1)
            doubleAttempt += 1
        } else {
            pendingGambleResult = doubleFailureResult(result, doubleAttempt + 1)
            doubleAttempt = 0
        }
    }

    fun validateMissions() {
        if (missionValidationBusy) return
        val draftTasks = state.tasks.filter { it.validationStatus == MissionValidationStatus.Draft }
        if (draftTasks.isEmpty()) {
            val validated = state.tasks.count { it.validationStatus == MissionValidationStatus.Validated }
            missionValidationStatus = "$validated/${state.tasks.size} missions already judged. Edit rejected ones before trying again."
            return
        }
        if (state.geminiApiKey.isBlank()) {
            missionValidationStatus = "Add your Gemini API key in Settings first."
            return
        }
        missionValidationBusy = true
        missionValidationStatus = "Judging your mission${if (draftTasks.size == 1) "" else "s"}. You're not allowed to touch while you wait."
        scope.launch {
            val result = runCatching {
                GeminiMissionClient.validate(
                    apiKey = state.geminiApiKey,
                    tasks = draftTasks,
                )
            }
            missionValidationBusy = false
            result
                .onSuccess { validations ->
                    val updatedState = state.applyMissionValidation(validations)
                    state = updatedState
                    val approved = updatedState.tasks.count { it.validationStatus == MissionValidationStatus.Validated }
                    missionValidationStatus = "$approved/${updatedState.tasks.size} missions judged. Now behave."
                }
                .onFailure {
                    missionValidationStatus = "Judgment failed. Check your API key. ${it.message ?: ""}"
                }
        }
    }

    LaunchedEffect(state) {
        AppStore.save(context, state)
    }

    LaunchedEffect(rejectedMissionBlock) {
        if (rejectedMissionBlock && selected != Screen.Tasks) {
            selected = Screen.Tasks
        }
    }

    LaunchedEffect(state.tutorialComplete, tutorialStep) {
        if (!state.tutorialComplete) {
            selected = tutorialPages[tutorialStep.coerceIn(tutorialPages.indices)].screen
            drawerState.close()
        }
    }

    LaunchedEffect(
        state.proofChecksEnabled,
        state.proofCheck,
        state.lockedUntilMillis,
        state.proofChancePercentPerHour,
        state.proofQuietStartHour,
        state.proofQuietEndHour,
    ) {
        while (state.proofChecksEnabled && state.proofCheck == null && state.lockedUntilMillis > System.currentTimeMillis()) {
            delay(60_000)
            val hour = Instant.now().atZone(ZoneId.systemDefault()).hour
            val allowedNow = !isQuietHour(hour, state.proofQuietStartHour, state.proofQuietEndHour)
            val perMinuteChance = state.proofChancePercentPerHour.coerceIn(0, 100) / 100f / 60f
            if (allowedNow && Random.nextFloat() < perMinuteChance) {
                startProofCheck()
                break
            }
        }
    }

    RouleteTheme {
        if (!state.onboardingComplete) {
            OnboardingScreen(
                onStart = { lockHours ->
                    val millis = System.currentTimeMillis() + lockHours.hours
                    state = state.copy(
                        tasks = state.tasks.map { it.copy(completed = false) },
                        lockedUntilMillis = millis,
                        betaTokens = max(state.betaTokens, CaseCostTokens),
                        onboardingComplete = true,
                        history = listOf(
                            HistoryEntry(
                                title = "Sentence started",
                                detail = formatHoursLabel(lockHours) + " starting lock selected. One welcome case funded.",
                                minutesDelta = lockHours.hoursMinutes,
                                kind = "onboarding",
                            )
                        ) + state.history,
                    )
                },
            )
            return@RouleteTheme
        }

        Box(modifier = Modifier.fillMaxSize()) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                AppDrawer(
                    selected = selected,
                    navBlocked = rejectedMissionBlock,
                    onSelect = { screen ->
                        if (!rejectedMissionBlock || screen == Screen.Tasks) {
                            selected = screen
                            scope.launch { drawerState.close() }
                        }
                    },
                )
            },
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Column {
                                Text(if (state.discreetMode) "Private Timer" else "betalocker", fontWeight = FontWeight.Black)
                            }
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = { scope.launch { drawerState.open() } },
                                modifier = Modifier.tutorialTarget(TutorialTarget.Menu) { target, bounds ->
                                    tutorialBounds[target] = bounds
                                },
                            ) {
                                Icon(Icons.Filled.Menu, contentDescription = "Menu")
                            }
                        },
                        actions = {
                            BetaTokenChip(count = state.betaTokens, modifier = Modifier.padding(end = 12.dp))
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Surface2.copy(alpha = 0.90f),
                            titleContentColor = Color.White,
                            navigationIconContentColor = Color.White,
                        ),
                    )
                },
                bottomBar = {
                    NavigationBar(
                        containerColor = Surface2.copy(alpha = 0.88f),
                        modifier = Modifier.clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
                    ) {
                        PrimaryScreens.forEach { screen ->
                            val navAllowed = !opening && (!rejectedMissionBlock || screen == Screen.Tasks)
                            NavigationBarItem(
                                selected = selected == screen,
                                onClick = { if (navAllowed || selected == screen) selected = screen },
                                enabled = navAllowed || selected == screen,
                                icon = { Icon(screen.icon, contentDescription = screen.label) },
                                label = { Text(screen.label, maxLines = 1, fontSize = 11.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Cyan,
                                    selectedTextColor = Cyan,
                                    indicatorColor = CyanDim,
                                    unselectedIconColor = Muted,
                                    unselectedTextColor = Muted,
                                ),
                            )
                        }
                    }
                }
            ) { padding ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    color = Ink,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(listOf(Ink, PanelAlt, Color(0xFF050608)))
                            )
                    ) {
                        AnimatedContent(
                            targetState = selected,
                            transitionSpec = {
                                if (reduceMotion) {
                                    fadeIn(tween(0)) togetherWith fadeOut(tween(0))
                                } else {
                                    fadeIn(tween(180)) togetherWith fadeOut(tween(180))
                                }
                            },
                            modifier = Modifier.fillMaxSize(),
                            label = "screen-transition",
                        ) { screen ->
                            when (screen) {
                        Screen.Dashboard -> DashboardScreen(
                            state = state,
                            proofBusy = proofBusy,
                            proofStatus = proofStatus,
                            onTaskChecked = { task, checked ->
                                state = state.toggleTask(task, checked)
                            },
                            onStartProofCheck = { startProofCheck() },
                            onVerifyProof = { bitmap ->
                                val proof = state.proofCheck ?: return@DashboardScreen
                                if (state.geminiApiKey.isBlank()) {
                                    proofStatus = "Add Gemini API key in Settings first."
                                    return@DashboardScreen
                                }
                                proofBusy = true
                                proofStatus = "Checking proof..."
                                scope.launch {
                                    val verdict = runCatching {
                                        GeminiProofClient.verify(
                                            context = context,
                                            apiKey = state.geminiApiKey,
                                            code = proof.code,
                                            bitmap = bitmap,
                                        )
                                    }.getOrElse {
                                        ProofVerdict(false, 0.0, "Gemini request failed: ${it.message ?: "unknown error"}")
                                    }
                                    proofBusy = false
                                    applyProofVerdict(verdict)
                                }
                            },
                            onTourTargetBounds = { target, bounds -> tutorialBounds[target] = bounds },
                        )

                        Screen.Tasks -> TasksScreen(
                            state = state,
                            strictActive = strictActive,
                            validationBusy = missionValidationBusy,
                            validationStatus = missionValidationStatus,
                            onValidateMissions = { validateMissions() },
                            onAdd = { title -> if (!strictActive) state = state.addTask(title) },
                            onEdit = { task, title ->
                                if (!strictActive && task.missionSource != MissionSource.Daily) {
                                    state = state.editTask(task, title)
                                }
                            },
                            onDelete = { task ->
                                if (!strictActive && task.missionSource != MissionSource.Daily) {
                                    state = state.copy(tasks = state.tasks.filterNot { it.id == task.id })
                                }
                            },
                            onTaskChecked = { task, checked -> state = state.toggleTask(task, checked) },
                            onTourTargetBounds = { target, bounds -> tutorialBounds[target] = bounds },
                        )

                        Screen.Gacha -> GamblingScreen(
                            state = state,
                            reel = reel,
                            opening = opening,
                            pendingPrize = pendingPrize,
                            spinToken = spinToken,
                            skipToken = skipToken,
                            onOpen = {
                                if (state.betaTokens >= CaseCostTokens && !opening) {
                                    val prize = drawPrize(state.casePrizes())
                                    pendingPrize = prize
                                    reel = generateReel(prize, state.casePrizes())
                                    opening = true
                                    spinToken += 1
                                    finishJob?.cancel()
                                    finishJob = scope.launch {
                                        delay(5_300)
                                        if (opening && pendingPrize == prize) {
                                            state = state.applyPrize(prize)
                                            opening = false
                                        }
                                    }
                                }
                            },
                            onSkip = {
                                if (opening) {
                                    scope.launch {
                                        finishOpeningNow()
                                    }
                                }
                            },
                            onCaseSelected = { case ->
                                if (!opening) {
                                    state = state.copy(selectedCase = case)
                                    reel = generateReel(null, prizeTables.getValue(case))
                                    pendingPrize = null
                                }
                            },
                            onGambleResult = { result -> offerGambleResult(result) },
                            onTourTargetBounds = { target, bounds -> tutorialBounds[target] = bounds },
                        )

                        Screen.Settings -> SettingsScreen(
                            state = state,
                            strictActive = strictActive,
                            tourActive = !state.tutorialComplete && tutorialPages[tutorialStep].target == TutorialTarget.GeminiSetup,
                            onDiscreetMode = { enabled -> state = state.copy(discreetMode = enabled) },
                            onStrictMode = { enabled -> state = state.copy(strictMode = enabled) },
                            onGeminiKey = { value -> state = state.copy(geminiApiKey = value) },
                            onProofChecksEnabled = { enabled -> state = state.copy(proofChecksEnabled = enabled) },
                            onProofChance = { value -> state = state.copy(proofChancePercentPerHour = value) },
                            onProofQuietStart = { value -> state = state.copy(proofQuietStartHour = value) },
                            onProofQuietEnd = { value -> state = state.copy(proofQuietEndHour = value) },
                            onProofFailurePenalty = { value -> state = state.copy(proofFailurePenaltyMinutes = value) },
                            onStartProofCheck = { startProofCheck() },
                            onResetDaily = { if (!strictActive) state = state.copy(tasks = state.tasks.map { it.copy(completed = false, rewardClaimedDay = null) }) },
                            onClearHistory = { if (!strictActive) state = state.copy(history = emptyList()) },
                            onResetLock = { state = state.resetLock() },
                            onReplayTutorial = { state = state.copy(tutorialComplete = false) },
                            onTourTargetBounds = { target, bounds -> tutorialBounds[target] = bounds },
                        )
                        Screen.History -> HistoryScreen(state.history)
                        Screen.Shop -> ShopScreen(
                            state = state,
                            onBuyShopMercy = { minutes, cost -> state = state.buyShopMercy(minutes, cost) },
                        )
                            }
                        }
                        pendingGambleResult?.let { result ->
                        GamblingResultModal(
                            result = result,
                            attempt = doubleAttempt,
                            onAccept = { acceptGambleResult(result) },
                            onDouble = { doubleGambleResult(result) },
                        )
                    }
                }
            }
        }
        if (!state.tutorialComplete) {
            TutorialOverlay(
                page = tutorialPages[tutorialStep],
                step = tutorialStep,
                pageCount = tutorialPages.size,
                targetBounds = tutorialBounds[tutorialPages[tutorialStep].target],
                onBack = { tutorialStep = (tutorialStep - 1).coerceAtLeast(0) },
                onNext = {
                    if (tutorialStep == tutorialPages.lastIndex) {
                        state = state.copy(tutorialComplete = true)
                        tutorialStep = 0
                    } else {
                        tutorialStep += 1
                    }
                },
                onSkip = {
                    state = state.copy(tutorialComplete = true)
                    tutorialStep = 0
                },
            )
        }
        }
    }
}
}

@Composable
private fun AppDrawer(
    selected: Screen,
    navBlocked: Boolean,
    onSelect: (Screen) -> Unit,
) {
    ModalDrawerSheet(drawerContainerColor = Surface2, drawerContentColor = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                "betalocker",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(10.dp),
            )
            HorizontalDivider(color = Divider)
            Text("Local tools", color = Muted, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 10.dp))
            PrimaryScreens.forEach { screen ->
                NavigationDrawerItem(
                    label = { Text(screen.label) },
                    selected = selected == screen,
                    onClick = { onSelect(screen) },
                    icon = { Icon(screen.icon, contentDescription = null) },
                    badge = if (screen == Screen.Tasks && navBlocked) {
                        { Text("Fix") }
                    } else null,
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = CyanDim,
                        selectedIconColor = Cyan,
                        selectedTextColor = Cyan,
                    ),
                )
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color.White.copy(alpha = 0.12f))
            Text("More", color = Muted, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 10.dp))
            DrawerScreens.forEach { screen ->
                if (screen == Screen.Settings) {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = Divider)
                }
                NavigationDrawerItem(
                    label = { Text(screen.label, color = if (navBlocked) Muted.copy(alpha = 0.55f) else Color.White) },
                    selected = selected == screen,
                    onClick = { if (!navBlocked) onSelect(screen) },
                    icon = { Icon(screen.icon, contentDescription = null, tint = if (navBlocked) Muted.copy(alpha = 0.55f) else Color.White) },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = CyanDim,
                        selectedIconColor = Cyan,
                        selectedTextColor = Cyan,
                    ),
                )
            }
            if (navBlocked) {
                Text(
                    "Rejected missions are blocking navigation. Edit or delete them on Task first.",
                    color = Coral,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(10.dp),
                )
            }
        }
    }
}

@Composable
private fun TutorialOverlay(
    page: TutorialPage,
    step: Int,
    pageCount: Int,
    targetBounds: Rect?,
    onBack: () -> Unit,
    onNext: () -> Unit,
    onSkip: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val uriHandler = LocalUriHandler.current
    var overlayBounds by remember { mutableStateOf(Rect.Zero) }
    val localTarget = targetBounds?.let { target ->
        Rect(
            left = target.left - overlayBounds.left,
            top = target.top - overlayBounds.top,
            right = target.right - overlayBounds.left,
            bottom = target.bottom - overlayBounds.top,
        )
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { overlayBounds = it.boundsInRoot() }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {},
            ),
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen },
        ) {
            drawRect(Color.Black.copy(alpha = 0.78f))
            localTarget?.let { target ->
                val padding = 8.dp.toPx()
                val left = (target.left - padding).coerceAtLeast(0f)
                val top = (target.top - padding).coerceAtLeast(0f)
                val right = (target.right + padding).coerceAtMost(size.width)
                val bottom = (target.bottom + padding).coerceAtMost(size.height)
                drawRoundRect(
                    color = Color.Transparent,
                    topLeft = androidx.compose.ui.geometry.Offset(left, top),
                    size = androidx.compose.ui.geometry.Size(right - left, bottom - top),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(12.dp.toPx()),
                    blendMode = BlendMode.Clear,
                )
                drawRoundRect(
                    color = Cyan,
                    topLeft = androidx.compose.ui.geometry.Offset(left, top),
                    size = androidx.compose.ui.geometry.Size(right - left, bottom - top),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(12.dp.toPx()),
                    style = Stroke(width = 2.dp.toPx()),
                )
            }
        }

        Card(
            modifier = Modifier
                .align(
                    if ((localTarget?.center?.y ?: 0f) > constraints.maxHeight * 0.55f) {
                        Alignment.TopCenter
                    } else {
                        Alignment.BottomCenter
                    }
                )
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF20252A)),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Text("${step + 1}/$pageCount", color = Cyan, fontWeight = FontWeight.Black)
                    Spacer(Modifier.weight(1f))
                    TextButton(onClick = onSkip) { Text("Skip") }
                }
                Text(page.title, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Black)
                Text(page.body, color = Muted, fontSize = 15.sp)
                if (page.actionLabel != null && page.actionUrl != null) {
                    OutlinedButton(
                        onClick = { uriHandler.openUri(page.actionUrl) },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(page.actionLabel)
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = onBack,
                        enabled = step > 0,
                        modifier = Modifier.weight(1f),
                    ) {
                        Text("Back")
                    }
                    Button(
                        onClick = onNext,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Cyan, contentColor = Ink),
                    ) {
                        Text(if (step == pageCount - 1) "Finish" else "Next")
                    }
                }
            }
        }
    }
}

@Composable
private fun ShopScreen(state: AppState, onBuyShopMercy: (Int, Int) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item { Header("Shop", "Spend BetaTokens on tiny mercy.") }
        item { StatPill("Balance", "${state.betaTokens} BetaTokens", Modifier.fillMaxWidth()) }
        items(
            listOf(
                Triple("-2h mercy", 2.hoursMinutes, 40),
                Triple("-4h mercy", 4.hoursMinutes, 80),
                Triple("-6h mercy", 6.hoursMinutes, 115),
            )
        ) { (label, minutes, cost) ->
            Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(8.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Column(Modifier.weight(1f)) {
                        Text(label, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("$cost BetaTokens", color = Muted, fontSize = 13.sp)
                    }
                    Button(
                        onClick = { onBuyShopMercy(minutes, cost) },
                        enabled = state.betaTokens >= cost,
                        colors = ButtonDefaults.buttonColors(containerColor = Cyan, contentColor = Ink),
                    ) {
                        Text("Buy")
                    }
                }
            }
        }
    }
}

@Composable
private fun PlaceholderScreen(title: String, subtitle: String, body: String) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item { Header(title, subtitle) }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(8.dp)) {
                Text(body, color = Muted, fontSize = 15.sp, modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
private fun OnboardingScreen(onStart: (Int) -> Unit) {
    var amountText by remember { mutableStateOf("24") }
    var unit by remember { mutableStateOf("hours") }
    val amount = amountText.toIntOrNull()?.coerceIn(1, 365) ?: 24
    val lockHours = if (unit == "days") amount * 24 else amount

    Surface(color = Ink, modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF080A0F), Charcoal, Color(0xFF050608))
                    )
                )
                .padding(22.dp),
            contentAlignment = Alignment.Center,
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Panel),
                shape = RoundedCornerShape(8.dp),
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Cyan.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(Icons.Filled.HourglassTop, contentDescription = null, tint = Cyan, modifier = Modifier.size(32.dp))
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Welcome to betalocker", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Black)
                        Text(
                            "Choose your starting sentence. Once you enter, a guided tour will move through the real controls with you.",
                            color = Muted,
                            fontSize = 15.sp,
                        )
                    }
                    OutlinedTextField(
                        value = amountText,
                        onValueChange = { value -> amountText = value.filter { it.isDigit() }.take(3) },
                        label = { Text("Starting lock") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("hours", "days").forEach { option ->
                            FilterChip(
                                selected = unit == option,
                                onClick = { unit = option },
                                label = { Text(option) },
                            )
                        }
                    }
                    Text("Starting sentence: ${formatHoursLabel(lockHours)}", color = Cyan, fontWeight = FontWeight.Bold)
                    Button(
                        enabled = amountText.isNotBlank(),
                        onClick = { onStart(lockHours.coerceAtLeast(1)) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Cyan, contentColor = Ink),
                    ) {
                        Icon(Icons.Filled.Lock, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Start ${formatHoursLabel(lockHours)} lock")
                    }
                }
            }
        }
    }
}

@Composable
private fun DashboardScreen(
    state: AppState,
    proofBusy: Boolean,
    proofStatus: String?,
    onTaskChecked: (DailyTask, Boolean) -> Unit,
    onStartProofCheck: () -> Unit,
    onVerifyProof: (Bitmap) -> Unit,
    onTourTargetBounds: (TutorialTarget, Rect) -> Unit,
) {
    var now by remember { mutableLongStateOf(System.currentTimeMillis()) }
    val context = androidx.compose.ui.platform.LocalContext.current
    val reduceMotion = (context.resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK) == 0
    val isLocked = state.proofCheck != null || state.lockedUntilMillis > now
    val glowAlpha = remember { Animatable(0.3f) }
    val tickAlpha = remember { Animatable(1f) }
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) onVerifyProof(bitmap)
    }
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) cameraLauncher.launch(null)
    }

    LaunchedEffect(isLocked, reduceMotion) {
        if (reduceMotion) {
            glowAlpha.snapTo(0.25f)
        } else if (isLocked) {
            while (true) {
                glowAlpha.animateTo(0.75f, tween(1500, easing = FastOutSlowInEasing))
                glowAlpha.animateTo(0.25f, tween(1500, easing = FastOutSlowInEasing))
            }
        } else {
            glowAlpha.animateTo(0.25f, tween(300))
        }
    }

    LaunchedEffect(reduceMotion) {
        while (true) {
            now = System.currentTimeMillis()
            if (reduceMotion) {
                tickAlpha.snapTo(1f)
            } else {
                launch {
                    tickAlpha.snapTo(0.8f)
                    tickAlpha.animateTo(1f, tween(120))
                }
            }
            delay(1_000)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            Header(
                title = if (state.discreetMode) "Private Timer" else "betalocker",
                subtitle = "Daily discipline, rigged rewards.",
            )
        }

        item {
            CountdownCard(
                remainingMillis = state.remainingMillis(now),
                lockedUntilMillis = state.lockedUntilMillis,
                frozen = state.proofCheck != null,
                glowAlpha = glowAlpha.value,
                tickAlpha = tickAlpha.value,
                modifier = Modifier.tutorialTarget(TutorialTarget.Countdown, onTourTargetBounds),
            )
        }

        item {
            ProofCheckCard(
                state = state,
                proofBusy = proofBusy,
                proofStatus = proofStatus,
                onStart = onStartProofCheck,
                onCapture = {
                    if (context.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraLauncher.launch(null)
                    } else {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                },
            )
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                GlassCard(modifier = Modifier.weight(1f), tier = 1) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("BetaTokens", color = Muted, fontSize = 13.sp)
                        Text(state.betaTokens.toString(), color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                }
                GlassCard(modifier = Modifier.weight(1f), tier = 1) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("Done", color = Muted, fontSize = 13.sp)
                        Text("${state.tasks.count { it.completed }}/${state.tasks.size}", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        item {
            Text(
                "Today's Missions",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
            )
        }

        val dashboardTasks = state.tasks.filter { !it.completed }.take(4)
        if (dashboardTasks.isEmpty()) {
            item {
                EmptyPanel("No tasks yet. Add a few rituals on the Tasks tab.")
            }
        } else {
            items(dashboardTasks, key = { it.id }) { task ->
                TaskRow(task = task, onChecked = { checked -> onTaskChecked(task, checked) })
            }
        }

        item {
            SectionTitle("Latest verdict")
            val latest = state.history.firstOrNull()
            if (latest == null) {
                EmptyPanel("No history yet. The house is waiting.")
            } else {
                HistoryRow(latest)
            }
        }
    }
}

@Composable
private fun TasksScreen(
    state: AppState,
    strictActive: Boolean,
    validationBusy: Boolean,
    validationStatus: String?,
    onValidateMissions: () -> Unit,
    onAdd: (String) -> Unit,
    onEdit: (DailyTask, String) -> Unit,
    onDelete: (DailyTask) -> Unit,
    onTaskChecked: (DailyTask, Boolean) -> Unit,
    onTourTargetBounds: (TutorialTarget, Rect) -> Unit,
) {
    var editorTask by remember { mutableStateOf<DailyTask?>(null) }
    var showAdd by remember { mutableStateOf(false) }
    var completingIds by remember { mutableStateOf<Set<String>>(emptySet()) }
    val scope = rememberCoroutineScope()
    val context = androidx.compose.ui.platform.LocalContext.current
    val reduceMotion = (context.resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK) == 0
    val visibleTasks = state.tasks.filter {
        !it.completed || it.id in completingIds ||
            it.validationStatus == MissionValidationStatus.Rejected ||
            it.validationStatus == MissionValidationStatus.Failed
    }
    val rejectedCount = state.tasks.count { it.validationStatus == MissionValidationStatus.Rejected }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    "Tasks",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                )
                Text(
                    "Validate missions, earn BetaTokens, gamble badly.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Muted,
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .tutorialTarget(TutorialTarget.MissionActions, onTourTargetBounds),
            ) {
                FilledTonalButton(
                    onClick = { showAdd = true },
                    enabled = !strictActive,
                    modifier = Modifier.weight(1f),
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Add")
                }
                Button(
                    onClick = onValidateMissions,
                    enabled = !validationBusy && state.tasks.isNotEmpty(),
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Cyan, contentColor = Ink),
                ) {
                    Text(if (validationBusy) "Checking..." else "Validate")
                }
            }
            validationStatus?.let {
                StatusBanner(message = it, isLoading = validationBusy)
            }
            if (rejectedCount > 0) {
                Text(
                    "$rejectedCount rejected mission${if (rejectedCount == 1) "" else "s"} blocking navigation. Edit or delete the trash first.",
                    color = Coral,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        if (visibleTasks.isEmpty()) {
            item { EmptyPanel("Your daily board is empty.") }
        } else {
            items(visibleTasks, key = { it.id }) { task ->
                val completing = task.id in completingIds
                val failed = task.validationStatus == MissionValidationStatus.Failed
                val scale = remember(task.id) { Animatable(1f) }
                LaunchedEffect(task.completed) {
                    if (reduceMotion) {
                        scale.snapTo(1f)
                    } else if (task.completed) {
                        scale.animateTo(1.04f, tween(100))
                        scale.animateTo(1f, tween(100))
                    } else {
                        scale.snapTo(1f)
                    }
                }
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            scaleX = scale.value
                            scaleY = scale.value
                        },
                    tier = 1,
                    backgroundColor = if (failed) CoralDim.copy(alpha = 0.10f) else null,
                    borderBrush = if (failed) Brush.linearGradient(listOf(CoralDim, CoralDim)) else null,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(3.dp)
                                .background(
                                    when (task.validationStatus) {
                                        MissionValidationStatus.Validated -> Mint
                                        MissionValidationStatus.Rejected -> Coral
                                        MissionValidationStatus.Failed -> Coral
                                        else -> Color.Transparent
                                    }
                                ),
                        )
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Checkbox(
                                checked = (task.validationStatus == MissionValidationStatus.Validated && task.completed) || completing,
                                enabled = task.validationStatus == MissionValidationStatus.Validated && !completing,
                                onCheckedChange = { checked ->
                                    if (checked) {
                                        completingIds = completingIds + task.id
                                        scope.launch {
                                            delay(620)
                                            onTaskChecked(task, true)
                                            completingIds = completingIds - task.id
                                        }
                                    } else {
                                        onTaskChecked(task, false)
                                    }
                                },
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        task.title,
                                        modifier = Modifier.weight(1f),
                                        fontWeight = FontWeight.SemiBold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        textDecoration = if (completing) TextDecoration.LineThrough else null,
                                        color = if (completing) Muted else Color.White,
                                    )
                                    if (task.missionSource == MissionSource.Daily) {
                                        Surface(
                                            shape = RoundedCornerShape(4.dp),
                                            color = CyanDim,
                                        ) {
                                            Text(
                                                text = "DAILY",
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                                style = MaterialTheme.typography.labelSmall,
                                                color = Cyan,
                                                fontWeight = FontWeight.Bold,
                                            )
                                        }
                                    }
                                }
                                if (failed) {
                                    Text(
                                        "✕ Failed yesterday",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Coral,
                                    )
                                }
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    task.difficulty?.let { difficulty ->
                                        DifficultyChip(difficulty)
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.betacoin),
                                            contentDescription = "BetaCoin",
                                            modifier = Modifier.size(16.dp),
                                        )
                                        Text("${task.rewardTokens}", color = Gold, fontSize = 13.sp)
                                    }
                                }
                                task.validationReason?.let {
                                    Text(it, color = Muted, fontSize = 11.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                }
                            }
                            if (task.missionSource != MissionSource.Daily) {
                                IconButton(onClick = { editorTask = task }, enabled = !strictActive) {
                                    Icon(Icons.Filled.Edit, contentDescription = "Edit")
                                }
                                IconButton(onClick = { onDelete(task) }, enabled = !strictActive) {
                                    Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Coral)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAdd) {
        TaskDialog(
            title = "Add task",
            initialTask = null,
            onDismiss = { showAdd = false },
            onSave = { text ->
                onAdd(text)
                showAdd = false
            },
        )
    }

    editorTask?.let { task ->
        TaskDialog(
            title = "Edit task",
            initialTask = task,
            onDismiss = { editorTask = null },
            onSave = { text ->
                onEdit(task, text)
                editorTask = null
            },
        )
    }
}

@Composable
private fun GamblingScreen(
    state: AppState,
    reel: List<Prize>,
    opening: Boolean,
    pendingPrize: Prize?,
    spinToken: Long,
    skipToken: Long,
    onOpen: () -> Unit,
    onSkip: () -> Unit,
    onCaseSelected: (CaseType) -> Unit,
    onGambleResult: (GamblingResult) -> Unit,
    onTourTargetBounds: (TutorialTarget, Rect) -> Unit,
) {
    val prizes = state.casePrizes()
    var machine by remember { mutableStateOf(GamblingMachine.Case) }
    var oddsExpanded by remember { mutableStateOf(false) }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item {
            Text("Gambling", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Black)
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.tutorialTarget(TutorialTarget.GamblingChoices, onTourTargetBounds),
            ) {
                items(GamblingMachine.entries.toList()) { option ->
                    FilterChip(
                        selected = machine == option,
                        onClick = { machine = option },
                        enabled = !opening,
                        label = { Text(option.title) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CyanDim,
                            selectedLabelColor = Cyan,
                        ),
                    )
                }
            }
        }

        when (machine) {
            GamblingMachine.Case -> {
                item {
                    CaseMachineCard(
                        state = state,
                        prizes = prizes,
                        reel = reel,
                        opening = opening,
                        pendingPrize = pendingPrize,
                        spinToken = spinToken,
                        skipToken = skipToken,
                        onOpen = onOpen,
                        onSkip = onSkip,
                        onCaseSelected = onCaseSelected,
                    )
                }
                item {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { oddsExpanded = !oddsExpanded }
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                "Odds",
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color.White,
                                modifier = Modifier.weight(1f),
                            )
                            Text(if (oddsExpanded) "Odds ▴" else "Odds ▾", color = Muted)
                        }
                        AnimatedVisibility(visible = oddsExpanded) {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                prizes.forEach { prize ->
                                    PrizeOddsRow(prize, prizes)
                                }
                            }
                        }
                    }
                }
            }
            GamblingMachine.Roulette -> item {
                RouletteMachineCard(betaTokens = state.betaTokens, onResult = onGambleResult)
            }
            GamblingMachine.Tower -> item {
                TowerMachineCard(betaTokens = state.betaTokens, onResult = onGambleResult)
            }
            GamblingMachine.Drop -> item {
                LockDropMachineCard(betaTokens = state.betaTokens, onResult = onGambleResult)
            }
        }
    }
}

@Composable
private fun CaseMachineCard(
    state: AppState,
    prizes: List<Prize>,
    reel: List<Prize>,
    opening: Boolean,
    pendingPrize: Prize?,
    spinToken: Long,
    skipToken: Long,
    onOpen: () -> Unit,
    onSkip: () -> Unit,
    onCaseSelected: (CaseType) -> Unit,
) {
    GlassCard(
        tier = 2,
        shape = RoundedCornerShape(20.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Casino, contentDescription = null, tint = Cyan, modifier = Modifier.size(32.dp))
                Spacer(Modifier.width(10.dp))
                Column(Modifier.weight(1f)) {
                    Text(state.selectedCase.title, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                    Text("${state.betaTokens} BetaTokens - cost $CaseCostTokens. ${state.selectedCase.subtitle}", color = Muted)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                CaseType.entries.forEach { case ->
                    FilterChip(
                        selected = state.selectedCase == case,
                        onClick = { onCaseSelected(case) },
                        enabled = !opening,
                        label = { Text(case.title.removeSuffix(" Case")) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CyanDim,
                            selectedLabelColor = Cyan,
                        ),
                    )
                }
            }
            ReelView(
                reel = reel,
                opening = opening,
                spinToken = spinToken,
                skipToken = skipToken,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                val insufficientTokens = state.betaTokens < CaseCostTokens
                Button(
                    enabled = !insufficientTokens && !opening,
                    onClick = onOpen,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (insufficientTokens) CoralDim else Cyan,
                        contentColor = if (insufficientTokens) Color.White else Ink,
                        disabledContainerColor = if (insufficientTokens) CoralDim else Cyan,
                        disabledContentColor = if (insufficientTokens) Color.White else Ink,
                    ),
                ) {
                    Icon(Icons.Filled.Lock, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(if (opening) "Opening..." else "Open Case — $CaseCostTokens")
                        if (!opening) {
                            Image(
                                painter = painterResource(id = R.drawable.betacoin),
                                contentDescription = "BetaCoin",
                                modifier = Modifier.size(16.dp),
                            )
                        }
                    }
                }
                AnimatedVisibility(opening) {
                    OutlinedButton(onClick = onSkip) {
                        Text("Skip")
                    }
                }
            }
            AnimatedVisibility(pendingPrize != null && !opening) {
                pendingPrize?.let {
                    OutcomePanel(it)
                }
            }
        }
    }
}

@Composable
private fun MachineTile(
    machine: GamblingMachine,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .height(54.dp)
            .clickable(enabled = enabled, onClick = onClick)
            .border(
                1.dp,
                if (selected) Cyan.copy(alpha = 0.85f) else Color.White.copy(alpha = 0.08f),
                RoundedCornerShape(8.dp),
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) Color(0xFF223437) else Panel,
        ),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(machine.title, color = Color.White, fontWeight = FontWeight.Black, maxLines = 1, fontSize = 16.sp)
            Text(machine.subtitle, color = Muted, fontSize = 10.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
private fun RouletteMachineCard(betaTokens: Int, onResult: (GamblingResult) -> Unit) {
    val scope = rememberCoroutineScope()
    val rotation = remember { Animatable(0f) }
    val ballAngle = remember { Animatable(-PI.toFloat() / 2f) }
    var spinning by remember { mutableStateOf(false) }
    var pointerBounce by remember { mutableStateOf(false) }
    var lastNumber by remember { mutableStateOf<Int?>(null) }
    var colorBets by remember { mutableStateOf(setOf(RouletteBetColor.Black)) }
    var numberBets by remember { mutableStateOf(setOf(17)) }
    val pointerOffset by animateDpAsState(
        targetValue = if (pointerBounce) 6.dp else (-2).dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessMedium,
        ),
        finishedListener = { pointerBounce = false },
        label = "pointerBounce",
    )
    val rouletteNumbers = listOf(0, 32, 15, 19, 4, 21, 2, 25, 17, 34, 6, 27, 13, 36, 11, 30, 8, 23, 10, 5, 24, 16, 33, 1, 20, 14, 31, 9, 22, 18, 29, 7, 28, 12, 35, 3, 26)

    GlassCard(tier = 2, shape = RoundedCornerShape(20.dp)) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(Modifier.fillMaxWidth()) {
                Text("Roulette", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Black)
                Text("Pick color and/or number. Matching bets stack. Cost $RouletteCostTokens BetaTokens.", color = Muted, fontSize = 13.sp)
            }
            BoxWithConstraints(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                val wheelSize = maxWidth - 16.dp
                Box(contentAlignment = Alignment.Center, modifier = Modifier.size(wheelSize)) {
                    RouletteWheel(
                        rotation = rotation.value,
                        numbers = rouletteNumbers,
                        landedNumber = if (!spinning) lastNumber else null,
                        ballAngle = ballAngle.value,
                        modifier = Modifier.fillMaxSize(),
                    )
                    Canvas(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .size(width = 14.dp, height = 22.dp)
                            .offset(y = pointerOffset),
                    ) {
                        val w = size.width
                        val h = size.height
                        val markerColor = if (!spinning && lastNumber != null) Cyan else Gold
                        val path = androidx.compose.ui.graphics.Path().apply {
                            moveTo(w / 2f, h)
                            lineTo(0f, 0f)
                            lineTo(w, 0f)
                            close()
                        }
                        drawPath(path, color = markerColor)
                        drawPath(
                            path,
                            color = Color.White.copy(alpha = 0.4f),
                            style = Stroke(width = 1.5f),
                        )
                        if (!spinning && lastNumber != null) {
                            drawCircle(
                                brush = Brush.radialGradient(
                                    colors = listOf(CyanGlow, Color.Transparent),
                                    radius = 24f,
                                    center = androidx.compose.ui.geometry.Offset(w / 2f, h),
                                ),
                                radius = 24f,
                                center = androidx.compose.ui.geometry.Offset(w / 2f, h),
                            )
                        }
                    }
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                Text("Bet on color", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    RouletteBetColor.entries.forEach { color ->
                        FilterChip(
                            selected = color in colorBets,
                            onClick = {
                                colorBets = if (color in colorBets) colorBets - color else colorBets + color
                            },
                            enabled = !spinning,
                            label = { Text(color.label) },
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
                Text("Bet on number", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                RouletteNumberTable(
                    selectedNumbers = numberBets,
                    enabled = !spinning,
                    onToggle = { number ->
                        numberBets = if (number in numberBets) numberBets - number else numberBets + number
                    },
                )
                Text(
                    "Active bets: ${rouletteBetSummary(colorBets, numberBets)}",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                TextButton(
                    onClick = {
                        colorBets = emptySet()
                        numberBets = emptySet()
                    },
                    enabled = !spinning && (colorBets.isNotEmpty() || numberBets.isNotEmpty()),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Clear all bets", color = Coral)
                }
            }
            lastNumber?.let { number ->
                Text(
                    "Last result: $number ${rouletteColorLabel(number)}",
                    color = if (rouletteBetWon(number, colorBets, numberBets)) Cyan else Coral,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                )
            }
            Button(
                enabled = betaTokens >= RouletteCostTokens && !spinning && (colorBets.isNotEmpty() || numberBets.isNotEmpty()),
                onClick = {
                    spinning = true
                    val landedIndex = Random.nextInt(rouletteNumbers.size)
                    val landed = rouletteNumbers[landedIndex]
                    scope.launch {
                        val slice = (2f * PI.toFloat()) / rouletteNumbers.size
                        val currentAngle = rotation.value
                        val targetFinalAngle = -(landedIndex + 0.5f) * slice
                        var forwardDelta = positiveModulo(targetFinalAngle - currentAngle, 2f * PI.toFloat())
                        if (forwardDelta < 0.01f) forwardDelta += 2f * PI.toFloat()
                        val totalDelta = 5f * 2f * PI.toFloat() + forwardDelta
                        val ballSpins = 8f
                        val ballStartAngle = ballAngle.value
                        val ballTotalDelta = -(ballSpins * 2f * PI.toFloat())

                        launch {
                            rotation.animateTo(
                                currentAngle + totalDelta,
                                animationSpec = tween(4_500, easing = FastOutSlowInEasing),
                            )
                        }
                        launch {
                            ballAngle.animateTo(
                                ballStartAngle + ballTotalDelta,
                                animationSpec = tween(4_200, easing = FastOutSlowInEasing),
                            )
                            ballAngle.snapTo(-PI.toFloat() / 2f)
                            pointerBounce = true
                        }
                        delay(4_500)
                        spinning = false
                        lastNumber = landed
                        onResult(rouletteResult(landed, colorBets, numberBets))
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Gold, contentColor = Ink),
            ) {
                Text(
                    when {
                        betaTokens < RouletteCostTokens -> "Need $RouletteCostTokens BetaTokens"
                        spinning -> "Spinning..."
                        else -> "Spin the wheel"
                    }
                )
            }
        }
    }
}

@Composable
private fun RouletteNumberTable(
    selectedNumbers: Set<Int>,
    enabled: Boolean,
    onToggle: (Int) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.fillMaxWidth()) {
        val zeroSelected = 0 in selectedNumbers
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(
                    if (zeroSelected) Gold else Color(0xFF159447).copy(alpha = 0.86f)
                )
                .border(
                    width = if (zeroSelected) 2.dp else 1.dp,
                    color = if (zeroSelected) Color.White else Color.White.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(6.dp),
                )
                .clickable(enabled = enabled) { onToggle(0) },
        ) {
            Text(
                text = "0",
                color = if (zeroSelected) Ink else Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
            )
        }

        (1..36).toList().chunked(9).forEach { rowNumbers ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                rowNumbers.forEach { number ->
                    val selected = number in selectedNumbers
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .height(30.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(
                                when {
                                    selected -> Gold
                                    else -> rouletteNumberColor(number).copy(alpha = 0.86f)
                                }
                            )
                            .border(
                                width = if (selected) 2.dp else 1.dp,
                                color = if (selected) Color.White else Color.White.copy(alpha = 0.10f),
                                shape = RoundedCornerShape(5.dp),
                            )
                            .clickable(enabled = enabled) { onToggle(number) },
                    ) {
                        Text(
                            text = number.toString(),
                            color = if (selected) Ink else Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RouletteWheel(
    rotation: Float,
    numbers: List<Int>,
    landedNumber: Int? = null,
    ballAngle: Float = -PI.toFloat() / 2f,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        val diameter = min(size.width, size.height)
        val topLeft = androidx.compose.ui.geometry.Offset((size.width - diameter) / 2f, (size.height - diameter) / 2f)
        val arcSize = androidx.compose.ui.geometry.Size(diameter, diameter)
        val slice = (2f * PI.toFloat()) / numbers.size
        drawCircle(Color(0xFF1A1208), radius = diameter / 2f - 1f)
        drawCircle(Gold, radius = diameter / 2f - 1f, style = Stroke(width = 6f))
        drawCircle(Color(0xFF8A6B1F), radius = diameter / 2f - 18f, style = Stroke(width = 2f))

        numbers.forEachIndexed { index, number ->
            val startAngleRad = rotation + index * slice - PI.toFloat() / 2f
            val color = when {
                number == 0 -> Color(0xFF1A5C30)
                rouletteIsRed(number) -> Color(0xFF8B1A1A)
                else -> Color(0xFF111111)
            }
            drawArc(
                color = color,
                startAngle = Math.toDegrees(startAngleRad.toDouble()).toFloat(),
                sweepAngle = Math.toDegrees(slice.toDouble()).toFloat() - 0.4f,
                useCenter = true,
                topLeft = topLeft,
                size = arcSize,
            )
            drawArc(
                color = Gold.copy(alpha = 0.38f),
                startAngle = Math.toDegrees(startAngleRad.toDouble()).toFloat(),
                sweepAngle = Math.toDegrees(slice.toDouble()).toFloat() - 0.4f,
                useCenter = true,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = 0.8f),
            )
            if (number == landedNumber) {
                drawArc(
                    color = Color.White.copy(alpha = 0.30f),
                    startAngle = Math.toDegrees(startAngleRad.toDouble()).toFloat(),
                    sweepAngle = Math.toDegrees(slice.toDouble()).toFloat() - 0.4f,
                    useCenter = true,
                    topLeft = topLeft,
                    size = arcSize,
                )
                drawArc(
                    color = Cyan.copy(alpha = 0.90f),
                    startAngle = Math.toDegrees(startAngleRad.toDouble()).toFloat(),
                    sweepAngle = Math.toDegrees(slice.toDouble()).toFloat() - 0.4f,
                    useCenter = true,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = 3f),
                )
            }

            val midAngle = startAngleRad + slice / 2f
            val labelRadius = diameter / 2f - 28f
            val lx = center.x + cos(midAngle.toDouble()).toFloat() * labelRadius
            val ly = center.y + sin(midAngle.toDouble()).toFloat() * labelRadius
            val textPaint = Paint().apply {
                this.color = android.graphics.Color.WHITE
                textSize = if (number == landedNumber) 16.sp.toPx() else 13.sp.toPx()
                textAlign = Paint.Align.CENTER
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                isAntiAlias = true
                if (number == landedNumber) {
                    setShadowLayer(6f, 0f, 0f, android.graphics.Color.WHITE)
                }
            }
            drawContext.canvas.nativeCanvas.save()
            drawContext.canvas.nativeCanvas.translate(lx, ly)
            drawContext.canvas.nativeCanvas.rotate(Math.toDegrees((midAngle + PI.toFloat() / 2f).toDouble()).toFloat())
            drawContext.canvas.nativeCanvas.drawText(number.toString(), 0f, 0f, textPaint)
            drawContext.canvas.nativeCanvas.restore()
        }

        drawCircle(color = Ink, radius = diameter * 0.17f)
        drawCircle(color = Gold, radius = diameter * 0.18f, style = Stroke(width = 4f))

        val ballRadius = diameter * 0.41f
        val bx = center.x + cos(ballAngle.toDouble()).toFloat() * ballRadius
        val by = center.y + sin(ballAngle.toDouble()).toFloat() * ballRadius
        drawCircle(
            color = Color.Black.copy(alpha = 0.45f),
            radius = 9f,
            center = androidx.compose.ui.geometry.Offset(bx + 2f, by + 2f),
        )
        drawCircle(
            color = Color.White,
            radius = 8f,
            center = androidx.compose.ui.geometry.Offset(bx, by),
        )
        drawCircle(
            color = Color.White.copy(alpha = 0.8f),
            radius = 3f,
            center = androidx.compose.ui.geometry.Offset(bx - 2f, by - 2f),
        )
    }
}

@Composable
private fun TowerMachineCard(betaTokens: Int, onResult: (GamblingResult) -> Unit) {
    var active by remember { mutableStateOf(false) }
    var floor by remember { mutableStateOf(0) }
    var collapsed by remember { mutableStateOf(false) }
    var revealed by remember { mutableStateOf<Map<Int, Int>>(emptyMap()) }
    val floors = 8
    val columns = 4
    val multipliers = listOf(1.27, 1.60, 2.03, 2.57, 3.26, 4.13, 5.23, 6.63)

    fun resetTower() {
        active = true
        floor = 0
        collapsed = false
        revealed = emptyMap()
    }

    GlassCard(tier = 2, shape = RoundedCornerShape(20.dp)) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("Cage Tower", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Black)
                    Text("Climb, cash out, or collapse. Cost $TowerCostTokens BetaTokens.", color = Muted, fontSize = 13.sp)
                }
                Text(
                    if (floor > 0) towerRewardLabel(floor, multipliers[floor - 1]) else "--",
                    color = Gold,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                (floors - 1 downTo 0).forEach { row ->
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text("${row + 1}", color = Muted, fontSize = 11.sp, modifier = Modifier.width(18.dp))
                        repeat(columns) { col ->
                            val isPicked = revealed[row] == col
                            val isCurrent = active && row == floor && !collapsed
                            val safeTint = if (isPicked) Color(0xFF14532D) else PanelAlt
                            val bg = when {
                                collapsed && isPicked && row == floor -> Color(0xFF7F1D1D)
                                isPicked -> safeTint
                                isCurrent -> Color(0xFF213840)
                                else -> PanelAlt.copy(alpha = 0.7f)
                            }
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(31.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(bg)
                                    .border(
                                        1.dp,
                                        when {
                                            collapsed && isPicked && row == floor -> Coral
                                            isPicked -> Gold
                                            isCurrent -> Cyan.copy(alpha = 0.5f)
                                            else -> Color.Transparent
                                        },
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable(enabled = isCurrent) {
                                        val hitCollapse = Random.nextInt(columns) == 0
                                        revealed = revealed + (row to col)
                                        if (hitCollapse) {
                                            active = false
                                            collapsed = true
                                            onResult(towerCollapseResult(row + 1))
                                        } else if (row == floors - 1) {
                                            active = false
                                            onResult(towerCashoutResult(row + 1, multipliers[row]))
                                        } else {
                                            floor += 1
                                        }
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    when {
                                        collapsed && isPicked && row == floor -> "X"
                                        isPicked -> "OK"
                                        else -> ""
                                    },
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                )
                            }
                        }
                        Text("${multipliers[row]}x", color = Gold, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.width(38.dp))
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { resetTower() },
                    enabled = !active && betaTokens >= TowerCostTokens,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Cyan, contentColor = Ink),
                ) { Text(if (betaTokens >= TowerCostTokens) "Start climb" else "Need $TowerCostTokens") }
                Button(
                    onClick = {
                        if (floor > 0) {
                            active = false
                            onResult(towerCashoutResult(floor, multipliers[floor - 1]))
                        }
                    },
                    enabled = active && floor > 0,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Gold, contentColor = Ink),
                ) { Text("Cash out") }
            }
        }
    }
}

@Composable
private fun LockDropMachineCard(betaTokens: Int, onResult: (GamblingResult) -> Unit) {
    val scope = rememberCoroutineScope()
    val progress = remember { Animatable(0f) }
    var dropping by remember { mutableStateOf(false) }
    var risk by remember { mutableStateOf(DropRisk.Mid) }
    var targetSlot by remember { mutableStateOf(6) }
    val slots = dropSlots(risk)

    GlassCard(tier = 2, shape = RoundedCornerShape(20.dp)) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Column(Modifier.fillMaxWidth()) {
                Text("Lock Drop", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Black)
                Text("Watch the sentence fall. Cost $DropCostTokens BetaTokens.", color = Muted, fontSize = 13.sp)
            }
            Box(modifier = Modifier.fillMaxWidth().height(300.dp), contentAlignment = Alignment.Center) {
                LockDropBoard(slots = slots, progress = progress.value, targetSlot = targetSlot)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                DropRisk.entries.forEach { option ->
                    FilterChip(
                        selected = risk == option,
                        onClick = { risk = option },
                        enabled = !dropping,
                        label = { Text(option.title) },
                        modifier = Modifier.weight(1f),
                    )
                }
            }
            Button(
                enabled = !dropping && betaTokens >= DropCostTokens,
                onClick = {
                    dropping = true
                    targetSlot = weightedDropSlot(slots)
                    scope.launch {
                        progress.snapTo(0f)
                        progress.animateTo(1f, animationSpec = tween(2_800, easing = FastOutSlowInEasing))
                        dropping = false
                        onResult(slots[targetSlot].toGamblingResult())
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Cyan, contentColor = Ink),
            ) {
                Text(
                    when {
                        betaTokens < DropCostTokens -> "Need $DropCostTokens BetaTokens"
                        dropping -> "Dropping..."
                        else -> "Send ball"
                    }
                )
            }
        }
    }
}

private data class DropSlot(val label: String, val minutesDelta: Int, val weight: Int, val color: Color)

@Composable
private fun LockDropBoard(slots: List<DropSlot>, progress: Float, targetSlot: Int) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val boardWidth = size.width
        val boardHeight = size.height
        val rows = 10
        for (row in 0 until rows) {
            val count = row + 4
            val y = 36f + row * ((boardHeight - 120f) / rows)
            repeat(count) { index ->
                val x = boardWidth * (index + 1f) / (count + 1f)
                drawCircle(Color.White.copy(alpha = 0.72f), radius = 4.5f, center = androidx.compose.ui.geometry.Offset(x, y))
                drawCircle(Cyan.copy(alpha = 0.24f), radius = 8f, center = androidx.compose.ui.geometry.Offset(x, y), style = Stroke(width = 1.4f))
            }
        }
        val slotWidth = boardWidth / slots.size
        slots.forEachIndexed { index, slot ->
            val slotLeft = index * slotWidth + 2f
            val slotTop = boardHeight - 54f
            val slotRight = slotLeft + slotWidth - 4f
            val slotBottom = slotTop + 36f

            drawRoundRect(
                brush = Brush.verticalGradient(
                    colors = listOf(slot.color.copy(alpha = 0.55f), slot.color),
                    startY = slotTop,
                    endY = slotBottom,
                ),
                topLeft = androidx.compose.ui.geometry.Offset(slotLeft, slotTop),
                size = androidx.compose.ui.geometry.Size(slotWidth - 4f, 36f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f),
            )
            drawRoundRect(
                color = Color.White.copy(alpha = 0.18f),
                topLeft = androidx.compose.ui.geometry.Offset(slotLeft, slotTop),
                size = androidx.compose.ui.geometry.Size(slotWidth - 4f, 36f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f),
                style = Stroke(width = 1.2f),
            )
        }
        if (progress >= 0.95f) {
            val highlightLeft = targetSlot * slotWidth + 1f
            drawRoundRect(
                color = Color.White.copy(alpha = 0.22f),
                topLeft = androidx.compose.ui.geometry.Offset(highlightLeft, boardHeight - 55f),
                size = androidx.compose.ui.geometry.Size(slotWidth - 2f, 38f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(9f, 9f),
                style = Stroke(width = 2.5f),
            )
        }
        val wave = sin(progress * PI * 8).toFloat()
        val targetX = slotWidth * targetSlot + slotWidth / 2f
        val startX = boardWidth / 2f
        val x = startX + (targetX - startX) * progress + wave * (1f - progress) * 28f
        val y = 18f + (boardHeight - 92f) * progress
        drawCircle(Gold.copy(alpha = 0.32f), radius = 17f, center = androidx.compose.ui.geometry.Offset(x, y))
        drawCircle(Color.White, radius = 10f, center = androidx.compose.ui.geometry.Offset(x, y))
        drawCircle(Gold, radius = 10f, center = androidx.compose.ui.geometry.Offset(x, y), style = Stroke(width = 3f))
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 254.dp),
    ) {
        slots.forEach { slot ->
            Text(
                slot.label,
                color = Color.White,
                fontSize = 9.sp,
                fontWeight = FontWeight.Black,
                maxLines = 1,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun ResultModal(title: String, body: String, color: Color, dismissible: Boolean, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.62f))
            .clickable { if (dismissible) onDismiss() },
        contentAlignment = Alignment.Center,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF3A1016)),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(title, color = color, fontSize = 32.sp, fontWeight = FontWeight.Black)
                Text(body, color = Color.White, fontSize = 15.sp)
                if (dismissible) {
                    TextButton(onClick = onDismiss) {
                        Text("Close")
                    }
                }
            }
        }
    }
}

@Composable
private fun GamblingResultModal(
    result: GamblingResult,
    attempt: Int,
    onAccept: () -> Unit,
    onDouble: () -> Unit,
) {
    val deltaColor = if (result.minutesDelta <= 0) Cyan else Coral
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.68f))
            .clickable { },
        contentAlignment = Alignment.Center,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF171018)),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(result.title, color = deltaColor, fontSize = 26.sp, fontWeight = FontWeight.Black)
                Text(result.detail, color = Muted, fontSize = 14.sp)
                Text(gamblingDeltaLabel(result.minutesDelta), color = deltaColor, fontSize = 34.sp, fontWeight = FontWeight.Black)
                if (result.minutesDelta < 0) {
                    Text(
                        if (attempt >= 4) "Last press. The house is done pretending."
                        else "Take the mercy, or press the button and let the house roll again.",
                        color = Muted,
                        fontSize = 12.sp,
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = onAccept, modifier = Modifier.weight(1f)) {
                            Text("Take it")
                        }
                        Button(
                            onClick = onDouble,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Coral, contentColor = Color.White),
                        ) {
                            Text("Double")
                        }
                    }
                } else {
                    Button(
                        onClick = onAccept,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Coral, contentColor = Color.White),
                    ) {
                        Text("Apply")
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryScreen(history: List<HistoryEntry>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item { Header("History", "Every little decision leaves a receipt.") }
        if (history.isEmpty()) {
            item { EmptyPanel("No records yet.") }
        } else {
            items(history, key = { it.id }) { entry ->
                HistoryRow(entry)
            }
        }
    }
}

@Composable
private fun ProofHistoryPanel(logs: List<ProofLog>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        if (logs.isEmpty()) {
            EmptyPanel("No AI proof verdicts yet.")
        } else {
            logs.take(6).forEach { log ->
                ProofLogRow(log)
            }
        }
    }
}

@Composable
private fun ProofLogRow(log: ProofLog) {
    Card(
        colors = CardDefaults.cardColors(containerColor = PanelAlt),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(if (log.passed) Cyan.copy(alpha = 0.18f) else Coral.copy(alpha = 0.18f)),
                contentAlignment = Alignment.Center,
            ) {
                Text(if (log.passed) "OK" else "NO", color = if (log.passed) Cyan else Coral, fontWeight = FontWeight.Black, fontSize = 11.sp)
            }
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    "${if (log.passed) "Passed" else "Failed"} - code ${log.code}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    "${(log.confidence * 100).roundToInt()}% confidence. ${log.reason}",
                    color = Muted,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                if (log.penaltyMinutes > 0) {
                    Text("+${formatPenaltyLabel(log.penaltyMinutes)} penalty applied", color = Coral, fontSize = 12.sp)
                }
            }
            Text(formatShortDate(log.timestampMillis), color = Muted, fontSize = 12.sp)
        }
    }
}

@Composable
private fun SettingsScreen(
    state: AppState,
    strictActive: Boolean,
    tourActive: Boolean,
    onDiscreetMode: (Boolean) -> Unit,
    onStrictMode: (Boolean) -> Unit,
    onGeminiKey: (String) -> Unit,
    onProofChecksEnabled: (Boolean) -> Unit,
    onProofChance: (Int) -> Unit,
    onProofQuietStart: (Int) -> Unit,
    onProofQuietEnd: (Int) -> Unit,
    onProofFailurePenalty: (Int) -> Unit,
    onStartProofCheck: () -> Unit,
    onResetDaily: () -> Unit,
    onClearHistory: () -> Unit,
    onResetLock: () -> Unit,
    onReplayTutorial: () -> Unit,
    onTourTargetBounds: (TutorialTarget, Rect) -> Unit,
) {
    var showApiDialog by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    LaunchedEffect(tourActive) {
        if (tourActive) {
            listState.animateScrollToItem(3)
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item { Header("Settings", "Local-only, sharp-edged, and simple.") }
        item {
            SettingsRow(
                title = "Discreet mode",
                subtitle = "Use a calmer dashboard title.",
                trailing = {
                    Switch(checked = state.discreetMode, onCheckedChange = onDiscreetMode)
                },
            )
        }
        item {
            SettingsRow(
                title = "Strict mode",
                subtitle = if (strictActive) {
                    "Active lock: task edits, resets, and history clearing are frozen."
                } else {
                    "During a lock, freeze rule changes and destructive settings."
                },
                trailing = {
                    Switch(checked = state.strictMode, onCheckedChange = onStrictMode)
                },
            )
        }
        item {
            SectionTitle("Gemini proof")
            Card(
                modifier = Modifier.tutorialTarget(TutorialTarget.GeminiSetup, onTourTargetBounds),
                colors = CardDefaults.cardColors(containerColor = Panel),
                shape = RoundedCornerShape(8.dp),
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    OutlinedButton(
                        onClick = { showApiDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        val hasKey = state.geminiApiKey.isNotBlank()
                        Icon(
                            if (hasKey) Icons.Filled.CheckCircle else Icons.Filled.Settings,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(if (hasKey) "Gemini API Key — configured ✓" else "Configure Gemini API Key")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text("Random proof checks", color = Color.White, fontWeight = FontWeight.Bold)
                            Text("Chance: ${state.proofChancePercentPerHour}% per hour. Quiet: ${hourLabel(state.proofQuietStartHour)}-${hourLabel(state.proofQuietEndHour)}.", color = Muted, fontSize = 13.sp)
                        }
                        Switch(checked = state.proofChecksEnabled, onCheckedChange = onProofChecksEnabled)
                    }
                    OutlinedTextField(
                        value = state.proofChancePercentPerHour.toString(),
                        onValueChange = { value -> onProofChance(value.filter { it.isDigit() }.take(3).toIntOrNull()?.coerceIn(0, 100) ?: 0) },
                        label = { Text("Random chance per hour") },
                        suffix = { Text("%") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = state.proofQuietStartHour.toString(),
                            onValueChange = { value -> onProofQuietStart(value.filter { it.isDigit() }.take(2).toIntOrNull()?.coerceIn(0, 23) ?: 0) },
                            label = { Text("Quiet start") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                        )
                        OutlinedTextField(
                            value = state.proofQuietEndHour.toString(),
                            onValueChange = { value -> onProofQuietEnd(value.filter { it.isDigit() }.take(2).toIntOrNull()?.coerceIn(0, 23) ?: 0) },
                            label = { Text("Quiet end") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                        )
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Failed proof penalty", color = Color.White, fontWeight = FontWeight.Bold)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                            listOf(0, 30, 60, 1.daysMinutes).forEach { minutes ->
                                FilterChip(
                                    selected = state.proofFailurePenaltyMinutes == minutes,
                                    onClick = { onProofFailurePenalty(minutes) },
                                    label = { Text(if (minutes == 0) "None" else "+${formatPenaltyLabel(minutes)}") },
                                )
                            }
                        }
                    }
                    OutlinedButton(
                        onClick = onStartProofCheck,
                        enabled = state.lockedUntilMillis > System.currentTimeMillis() && state.proofCheck == null,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("Trigger proof now")
                    }
                }
            }
        }
        item {
            SectionTitle("AI proof history")
            ProofHistoryPanel(state.proofHistory)
        }
        item {
            SettingsRow(
                title = "Tutorial",
                subtitle = "Replay the guided spotlight tour.",
                trailing = {
                    OutlinedButton(onClick = onReplayTutorial) {
                        Text("Replay tour")
                    }
                }
            )
        }
        item {
            SettingsRow(
                title = "Cage timer",
                subtitle = "Reset the chastity lock countdown to unlocked.",
                trailing = {
                    OutlinedButton(onClick = onResetLock) {
                        Text("Reset")
                    }
                },
            )
        }
        item {
            SettingsRow(
                title = "Daily reset",
                subtitle = "Clear today's completed task checks now.",
                trailing = {
                    OutlinedButton(onClick = onResetDaily, enabled = !strictActive) {
                        Text("Reset")
                    }
                },
            )
        }
        item {
            SettingsRow(
                title = "History",
                subtitle = "Remove local task and gacha records.",
                trailing = {
                    OutlinedButton(onClick = onClearHistory, enabled = !strictActive) {
                        Text("Clear")
                    }
                },
            )
        }
    }

    if (showApiDialog) {
        var draftKey by remember { mutableStateOf(state.geminiApiKey) }
        AlertDialog(
            onDismissRequest = { showApiDialog = false },
            title = { Text("Gemini API Key") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Used for mission validation and proof checks. Stored locally only.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Muted,
                    )
                    OutlinedTextField(
                        value = draftKey,
                        onValueChange = { draftKey = it },
                        label = { Text("API Key") },
                        placeholder = { Text("AIza...") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Text(
                        "Models used (auto): gemini-3.6-flash → 3.5-flash → 3.5-flash-lite → 3.1-flash → 3.1-flash-lite",
                        style = MaterialTheme.typography.labelSmall,
                        color = Muted.copy(alpha = 0.7f),
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    onGeminiKey(draftKey.trim())
                    showApiDialog = false
                }) { Text("Save") }
            },
            dismissButton = {
                TextButton(onClick = { showApiDialog = false }) { Text("Cancel") }
            },
        )
    }
}

@Composable
private fun Header(title: String, subtitle: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(title, color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Black)
        Text(subtitle, color = Muted, fontSize = 15.sp)
    }
}

@Composable
private fun CountdownCard(
    remainingMillis: Long,
    lockedUntilMillis: Long,
    frozen: Boolean,
    glowAlpha: Float = 0.25f,
    tickAlpha: Float = 1f,
    modifier: Modifier = Modifier,
) {
    GlassCard(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                drawRoundRect(
                    color = CyanGlow.copy(alpha = glowAlpha),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(20.dp.toPx()),
                    style = Stroke(width = 8.dp.toPx()),
                )
            },
        tier = 2,
        shape = RoundedCornerShape(20.dp),
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Cyan.copy(alpha = 0.16f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(Icons.Filled.Lock, contentDescription = null, tint = Cyan)
                }
                Spacer(Modifier.width(10.dp))
                Column {
                    Text("Locked timer", color = Muted, fontSize = 13.sp)
                    Text(formatDateTime(lockedUntilMillis), color = Color.White, fontWeight = FontWeight.SemiBold)
                }
            }
            val totalSeconds = remainingMillis / 1_000
            val days = totalSeconds / 86_400
            val hours = (totalSeconds % 86_400) / 3_600
            val minutes = (totalSeconds % 3_600) / 60
            val seconds = totalSeconds % 60
            if (days > 0) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer { alpha = tickAlpha },
                    verticalArrangement = Arrangement.spacedBy((-4).dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(
                            text = "${days}d",
                            color = Cyan,
                            fontWeight = FontWeight.Black,
                            fontSize = 44.sp,
                            fontFamily = FontFamily.Monospace,
                        )
                        Text(
                            text = "${hours}h",
                            color = Cyan,
                            fontWeight = FontWeight.Black,
                            fontSize = 44.sp,
                            fontFamily = FontFamily.Monospace,
                        )
                    }
                    Text(
                        text = "%02dm %02ds".format(minutes, seconds),
                        color = Cyan.copy(alpha = 0.65f),
                        fontWeight = FontWeight.Medium,
                        fontSize = 22.sp,
                        fontFamily = FontFamily.Monospace,
                    )
                }
            } else {
                Text(
                    text = "%02d:%02d:%02d".format(hours, minutes, seconds),
                    color = Cyan,
                    fontWeight = FontWeight.Black,
                    fontSize = 52.sp,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer { alpha = tickAlpha },
                )
            }
            Text(
                text = when {
                    frozen -> "Frozen until proof passes."
                    remainingMillis == 0L -> "Unlocked. Suspiciously generous."
                    else -> "Time left before mercy."
                },
                color = Muted,
                fontStyle = if (remainingMillis == 0L && !frozen) FontStyle.Italic else FontStyle.Normal,
            )
        }
    }
}

@Composable
private fun ProofCheckCard(
    state: AppState,
    proofBusy: Boolean,
    proofStatus: String?,
    onStart: () -> Unit,
    onCapture: () -> Unit,
) {
    val proof = state.proofCheck
    GlassCard(
        tier = 1,
        shape = RoundedCornerShape(20.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("Proof check", color = Color.White, fontWeight = FontWeight.Bold)
                    Text(
                        if (proof == null) {
                            "Random checks can freeze the timer until verified."
                        } else {
                            "Code ${proof.code} - write it on paper or show it on another screen in the photo."
                        },
                        color = Muted,
                        fontSize = 13.sp,
                    )
                }
                Switch(checked = state.proofChecksEnabled, onCheckedChange = null, enabled = false)
            }

            proofStatus?.let {
                Text(it, color = if (it.contains("passed", ignoreCase = true)) Cyan else Coral, fontSize = 13.sp)
            }
            proof?.lastMessage?.let {
                Text("Last reason: $it", color = Muted, fontSize = 12.sp)
            }

            if (proof == null) {
                AnimatedVisibility(visible = state.proofChecksEnabled) {
                    OutlinedButton(
                        onClick = onStart,
                        enabled = state.lockedUntilMillis > System.currentTimeMillis(),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("Start test check")
                    }
                }
            } else {
                Button(
                    onClick = onCapture,
                    enabled = !proofBusy && state.geminiApiKey.isNotBlank(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Cyan, contentColor = Ink),
                ) {
                    Text(if (proofBusy) "Checking..." else "Capture proof")
                }
                if (state.geminiApiKey.isBlank()) {
                    Text("Add Gemini API key in Settings first.", color = Coral, fontSize = 13.sp)
                } else {
                    Text("The 5-digit code must be visible in the camera shot.", color = Muted, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun StatPill(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = PanelAlt),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(label, color = Muted, fontSize = 13.sp)
            Text(value, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun TimeButton(label: String, minutes: Int, modifier: Modifier, onManualTime: (Int, String) -> Unit) {
    FilledTonalButton(
        onClick = { onManualTime(minutes, label) },
        modifier = modifier,
    ) {
        Icon(if (minutes >= 0) Icons.Filled.Add else Icons.Filled.Remove, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(4.dp))
        Text(label, maxLines = 1)
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier.padding(top = 4.dp),
    )
}

@Composable
private fun TaskRow(task: DailyTask, onChecked: (Boolean) -> Unit) {
    val failed = task.validationStatus == MissionValidationStatus.Failed
    Card(
        modifier = Modifier.border(
            width = 1.dp,
            color = if (failed) CoralDim else Color.Transparent,
            shape = RoundedCornerShape(8.dp),
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (failed) CoralDim.copy(alpha = 0.10f) else Panel,
        ),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(3.dp)
                    .background(if (failed) Coral else Color.Transparent),
            )
            Checkbox(
                checked = task.completed,
                enabled = task.validationStatus == MissionValidationStatus.Validated,
                onCheckedChange = onChecked,
            )
            Column(Modifier.weight(1f)) {
                Text(task.title, color = Color.White, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                if (failed) {
                    Text(
                        "✕ Failed yesterday",
                        style = MaterialTheme.typography.labelSmall,
                        color = Coral,
                    )
                }
                Text(taskRewardLine(task), color = taskStatusColor(task), fontSize = 13.sp)
            }
            if (task.completed) {
                Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = Cyan)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskDialog(
    title: String,
    initialTask: DailyTask?,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
) {
    var text by remember { mutableStateOf(initialTask?.title.orEmpty()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Task") },
                    singleLine = true,
                )
                Text(
                    "Gemini validates the board and assigns BetaTokens after saving.",
                    color = Muted,
                    fontSize = 13.sp,
                )
            }
        },
        confirmButton = {
            Button(
                enabled = text.isNotBlank(),
                onClick = { onSave(text.trim()) },
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
    )
}

@Composable
private fun ReelView(
    reel: List<Prize>,
    opening: Boolean,
    spinToken: Long,
    skipToken: Long,
) {
    val scrollState = rememberScrollState()
    val density = LocalDensity.current

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF080A0F))
            .border(1.dp, Cyan.copy(alpha = 0.28f), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center,
    ) {
        val tileWidth = 100.dp
        val tileGap = 6.dp
        val targetScrollPx = with(density) {
            (((tileWidth + tileGap) * WinningPrizeIndex) + (tileWidth / 2) - (maxWidth / 2))
                .toPx()
                .roundToInt()
                .coerceAtLeast(0)
        }

        LaunchedEffect(spinToken, targetScrollPx) {
            if (spinToken > 0L) {
                scrollState.scrollTo(0)
                scrollState.animateScrollTo(
                    value = targetScrollPx,
                    animationSpec = tween(durationMillis = 5_000, easing = FastOutSlowInEasing),
                )
            }
        }

        LaunchedEffect(skipToken, targetScrollPx) {
            if (skipToken > 0L) {
                scrollState.animateScrollTo(
                    value = targetScrollPx,
                    animationSpec = tween(durationMillis = 160),
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxHeight()
                .horizontalScroll(scrollState, enabled = false)
                .padding(horizontal = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(tileGap),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            reel.forEachIndexed { index, prize ->
                val isCenter = index == WinningPrizeIndex
                Box(
                    modifier = Modifier.graphicsLayer {
                        val scale = if (isCenter) 1.0f else 0.85f
                        scaleX = scale
                        scaleY = scale
                        alpha = if (isCenter) 1.0f else 0.6f
                    }
                ) {
                    PrizeTile(prize = prize, width = tileWidth)
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .width(86.dp)
                .fillMaxHeight()
                .background(Brush.horizontalGradient(listOf(Color(0xFF080A0F), Color.Transparent))),
        )
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .width(86.dp)
                .fillMaxHeight()
                .background(Brush.horizontalGradient(listOf(Color.Transparent, Color(0xFF080A0F)))),
        )
        Box(
            modifier = Modifier
                .width(3.dp)
                .height(122.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color.White, Cyan, Color.White, Color.Transparent)
                    )
                ),
        )
    }
}

@Composable
private fun PrizeTile(prize: Prize, width: androidx.compose.ui.unit.Dp) {
    Box(
        modifier = Modifier
            .size(width = width, height = 90.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                Brush.verticalGradient(
                    listOf(rarityColor(prize.rarity).copy(alpha = 0.95f), rarityColor(prize.rarity).copy(alpha = 0.36f))
                )
            )
            .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(8.dp)),
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(5.dp)
                .background(rarityColor(prize.rarity)),
        )
        Text(
            text = prize.label.take(18),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 8.dp),
            color = Color.White,
            fontWeight = FontWeight.Black,
            fontSize = 12.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun OutcomePanel(prize: Prize) {
    val relief = prize.minutesDelta < 0
    Card(
        colors = CardDefaults.cardColors(containerColor = rarityColor(prize.rarity).copy(alpha = 0.14f)),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(if (relief) "Mercy found a loophole." else "Sentence updated. Naturally.", color = Color.White, fontWeight = FontWeight.Bold)
            Text(prize.label, color = rarityColor(prize.rarity))
            Text(prize.description, color = Muted, fontSize = 13.sp)
        }
    }
}

@Composable
private fun PrizeOddsRow(prize: Prize, prizes: List<Prize>) {
    Card(
        colors = CardDefaults.cardColors(containerColor = PanelAlt),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(rarityColor(prize.rarity))
            )
            Spacer(Modifier.width(10.dp))
            Column(Modifier.weight(1f)) {
                Text(prize.label, color = Color.White, fontWeight = FontWeight.SemiBold)
                Text(prize.description, color = Muted, fontSize = 13.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            AssistChip(onClick = {}, label = { Text(prize.oddsText(prizes)) })
        }
    }
}

@Composable
private fun HistoryRow(entry: HistoryEntry) {
    val accent = entry.rarity?.let { runCatching { rarityColor(Rarity.valueOf(it)) }.getOrNull() }
        ?: if (entry.minutesDelta >= 0) Coral else Cyan
    Card(
        colors = CardDefaults.cardColors(containerColor = Panel),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(accent.copy(alpha = 0.16f)),
                contentAlignment = Alignment.Center,
            ) {
                Text(if (entry.minutesDelta >= 0) "+" else "-", color = accent, fontWeight = FontWeight.Black)
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(entry.title, color = Color.White, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(entry.detail, color = Muted, fontSize = 13.sp)
            }
            Text(formatShortDate(entry.timestampMillis), color = Muted, fontSize = 12.sp)
        }
    }
}

@Composable
private fun SettingsRow(title: String, subtitle: String, trailing: @Composable () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Panel),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(Modifier.weight(1f)) {
                Text(title, color = Color.White, fontWeight = FontWeight.Bold)
                Text(subtitle, color = Muted, fontSize = 13.sp)
            }
            trailing()
        }
    }
}

@Composable
private fun EmptyPanel(text: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = PanelAlt),
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = Muted,
        )
    }
}

@Composable
private fun GlassCard(
    modifier: Modifier = Modifier,
    tier: Int = 1,
    shape: Shape = RoundedCornerShape(20.dp),
    backgroundColor: Color? = null,
    borderBrush: Brush? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    val bgColor = when (tier) {
        1 -> Surface1
        2 -> Surface2
        3 -> Surface3
        else -> Surface1
    }
    val rimAlpha = when (tier) {
        1 -> 0.07f
        2 -> 0.10f
        3 -> 0.14f
        else -> 0.07f
    }
    Box(
        modifier = modifier
            .clip(shape)
            .background(backgroundColor ?: bgColor.copy(alpha = if (Build.VERSION.SDK_INT >= 31) 0.65f else 0.90f))
            .border(
                width = 1.dp,
                brush = borderBrush ?: Brush.linearGradient(
                    0f to Color.White.copy(alpha = rimAlpha + 0.08f),
                    0.5f to Color.White.copy(alpha = rimAlpha),
                    1f to Color.White.copy(alpha = rimAlpha + 0.04f),
                ),
                shape = shape,
            ),
        content = content,
    )
}

@Composable
private fun BetaTokenChip(count: Int, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = GoldDim,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.betacoin),
                contentDescription = "BetaCoin",
                modifier = Modifier.size(16.dp),
            )
            Text(
                "$count",
                style = MaterialTheme.typography.labelMedium,
                color = Gold,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Composable
private fun DifficultyChip(difficulty: MissionDifficulty, modifier: Modifier = Modifier) {
    val (bg, fg) = when (difficulty) {
        MissionDifficulty.Easy     -> MintDim to Mint
        MissionDifficulty.Medium   -> GoldDim to Gold
        MissionDifficulty.Hard     -> CoralDim to Coral
        MissionDifficulty.Hardcore -> VioletDim to Violet
    }
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = bg,
    ) {
        Text(
            text = difficulty.label,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
            style = MaterialTheme.typography.labelSmall,
            color = fg,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
private fun RarityChip(rarity: Rarity, modifier: Modifier = Modifier) {
    val (bg, fg, label) = when (rarity) {
        Rarity.MilSpec    -> Triple(RarityMilSpec.copy(alpha = 0.15f), RarityMilSpec, "Mil-Spec")
        Rarity.Restricted -> Triple(RarityRestricted.copy(alpha = 0.15f), RarityRestricted, "Restricted")
        Rarity.Classified -> Triple(RarityClassified.copy(alpha = 0.15f), RarityClassified, "Classified")
        Rarity.Covert     -> Triple(RarityCovert.copy(alpha = 0.15f), RarityCovert, "Covert")
        Rarity.Gold       -> Triple(RarityGold.copy(alpha = 0.20f), RarityGold, "Gold")
    }
    Surface(modifier = modifier, shape = CircleShape, color = bg) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
            style = MaterialTheme.typography.labelSmall,
            color = fg,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
private fun StatusBanner(
    message: String,
    isLoading: Boolean = false,
    isError: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val bg = if (isError) CoralDim else CyanDim
    val fg = if (isError) Coral else Cyan
    Column(modifier = modifier) {
        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                color = if (isError) Coral else Cyan,
                trackColor = bg,
            )
        }
        Surface(
            shape = if (isLoading) RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                    else RoundedCornerShape(12.dp),
            color = bg,
        ) {
            Text(
                text = message,
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                style = MaterialTheme.typography.bodySmall,
                color = fg,
            )
        }
    }
}

@Composable
private fun RouleteTheme(content: @Composable () -> Unit) {
    val colors = darkColorScheme(
        primary = Cyan,
        onPrimary = Ink,
        primaryContainer = CyanDim,
        onPrimaryContainer = CyanOnDark,
        secondary = Gold,
        onSecondary = Ink,
        secondaryContainer = GoldDim,
        onSecondaryContainer = Gold,
        tertiary = Mint,
        onTertiary = Ink,
        tertiaryContainer = MintDim,
        onTertiaryContainer = Mint,
        error = Coral,
        errorContainer = CoralDim,
        background = Ink,
        onBackground = Color.White,
        surface = Surface1,
        onSurface = Color.White,
        surfaceVariant = Surface2,
        onSurfaceVariant = Muted,
        outline = Rim,
        outlineVariant = Divider,
    )
    MaterialTheme(colorScheme = colors, content = content)
}

private fun AppState.withDailyReset(): AppState {
    val today = todayKey()
    if (lastResetDay == today) return this

    val incompleteValidated = tasks.count { task ->
        !task.completed && task.validationStatus == MissionValidationStatus.Validated
    }
    val penaltyMinutes = incompleteValidated * 10

    val penaltyEntry = if (penaltyMinutes > 0) {
        HistoryEntry(
            title = "Midnight penalty",
            detail = "$incompleteValidated mission${if (incompleteValidated == 1) "" else "s"} " +
                "missed — +${penaltyMinutes}m added to sentence.",
            minutesDelta = penaltyMinutes,
            kind = "penalty",
        )
    } else {
        null
    }

    val resetTasks = tasks.map { task ->
        when {
            task.completed -> task.copy(
                completed = false,
                rewardClaimedDay = null,
                validationStatus = if (task.validationStatus == MissionValidationStatus.Failed) {
                    MissionValidationStatus.Draft
                } else {
                    task.validationStatus
                },
            )
            task.validationStatus == MissionValidationStatus.Validated -> task.copy(
                completed = false,
                rewardClaimedDay = null,
                validationStatus = MissionValidationStatus.Failed,
            )
            task.validationStatus == MissionValidationStatus.Failed -> task.copy(
                completed = false,
                rewardClaimedDay = null,
                validationStatus = MissionValidationStatus.Draft,
            )
            else -> task.copy(completed = false, rewardClaimedDay = null)
        }
    }
    val dailyIds = DAILY_AUTO_MISSIONS.map { it.id }.toSet()
    val userTasks = resetTasks.filterNot { it.id in dailyIds }
    val freshDailyTasks = DAILY_AUTO_MISSIONS.map { mission ->
        mission.copy(completed = false, rewardClaimedDay = null)
    }
    val allTasks = freshDailyTasks + userTasks

    return copy(
        tasks = allTasks,
        lastResetDay = today,
        lockedUntilMillis = if (penaltyMinutes > 0) {
            lockedUntilMillis + penaltyMinutes.minutesMillis
        } else {
            lockedUntilMillis
        },
        history = penaltyEntry?.let { listOf(it) + history } ?: history,
    )
}

private fun AppState.addTask(title: String): AppState =
    copy(tasks = tasks + DailyTask(title = title))

private fun AppState.editTask(task: DailyTask, title: String): AppState =
    copy(
        tasks = tasks.map {
            if (it.id == task.id) {
                it.copy(
                    title = title,
                    rewardTokens = 0,
                    validationStatus = MissionValidationStatus.Draft,
                    difficulty = null,
                    validationReason = "Edited mission needs validation.",
                    missionSource = MissionSource.Custom,
                )
            } else {
                it
            }
        }
    )

private fun AppState.remainingMillis(now: Long): Long =
    proofCheck?.frozenRemainingMillis ?: max(0L, lockedUntilMillis - now)

private fun AppState.toggleTask(task: DailyTask, checked: Boolean): AppState {
    if (task.completed == checked) return this
    val today = todayKey()
    val canClaimReward = checked &&
        task.rewardClaimedDay != today &&
        task.validationStatus == MissionValidationStatus.Validated &&
        task.rewardTokens > 0
    val earned = if (canClaimReward) task.rewardTokens else 0
    val updatedTasks = tasks.map {
        if (it.id == task.id) {
            it.copy(
                completed = checked,
                rewardClaimedDay = if (canClaimReward) today else it.rewardClaimedDay,
            )
        } else {
            it
        }
    }
    val entry = if (canClaimReward) {
        HistoryEntry(
            title = "Task completed",
            detail = "${task.title} earned $earned BetaTokens.",
            minutesDelta = 0,
            kind = "task",
        )
    } else {
        null
    }
    return copy(
        tasks = updatedTasks,
        betaTokens = betaTokens + earned,
        history = entry?.let { listOf(it) + history } ?: history,
    )
}

private fun AppState.applyMissionValidation(results: List<MissionValidationResult>): AppState {
    val byId = results.associateBy { it.id }
    val updated = tasks.map { task ->
        val result = byId[task.id]
        when {
            result == null -> task
            result.valid && result.difficulty != null -> task.copy(
                rewardTokens = result.difficulty.tokens,
                validationStatus = MissionValidationStatus.Validated,
                difficulty = result.difficulty,
                validationReason = result.reason,
            )
            else -> task.copy(
                rewardTokens = 0,
                completed = false,
                validationStatus = MissionValidationStatus.Rejected,
                difficulty = null,
                validationReason = result.reason,
            )
        }
    }
    val approved = updated.count { it.validationStatus == MissionValidationStatus.Validated }
    val entry = HistoryEntry(
        title = "Missions validated",
        detail = "$approved/${tasks.size} missions approved by Gemini.",
        minutesDelta = 0,
        kind = "task",
    )
    return copy(tasks = updated, history = listOf(entry) + history)
}

private fun AppState.adjustTime(minutes: Int, kind: String, label: String): AppState {
    val base = max(lockedUntilMillis, System.currentTimeMillis())
    val newLockedUntil = max(System.currentTimeMillis(), base + minutes.minutesMillis)
    val entry = HistoryEntry(
        title = "$kind adjustment",
        detail = "$label applied to timer.",
        minutesDelta = minutes,
        kind = kind.lowercase(),
    )
    return copy(lockedUntilMillis = newLockedUntil, history = listOf(entry) + history)
}

private fun AppState.resetLock(): AppState {
    val entry = HistoryEntry(
        title = "Cage timer reset",
        detail = "Chastity lock countdown reset from Settings.",
        minutesDelta = 0,
        kind = "settings",
    )
    return copy(
        lockedUntilMillis = System.currentTimeMillis(),
        proofCheck = null,
        history = listOf(entry) + history,
    )
}

private fun AppState.applyPrize(prize: Prize): AppState {
    val resolvedMinutes = resolvePrizeMinutes(prize, lockedUntilMillis)
    val base = max(lockedUntilMillis, System.currentTimeMillis())
    val newLockedUntil = max(System.currentTimeMillis(), base + resolvedMinutes.minutesMillis)
    val entry = HistoryEntry(
        title = "Case opened",
        detail = "${prize.label}: ${prize.description}",
        minutesDelta = resolvedMinutes,
        kind = "gacha",
        rarity = prize.rarity.name,
    )
    return copy(
        lockedUntilMillis = newLockedUntil,
        betaTokens = max(0, betaTokens - CaseCostTokens),
        history = listOf(entry) + history,
    )
}

private fun AppState.applyGamblingResult(result: GamblingResult): AppState {
    val base = max(lockedUntilMillis, System.currentTimeMillis())
    val newLockedUntil = max(System.currentTimeMillis(), base + result.minutesDelta.minutesMillis)
    val cost = gamblingCostTokens(result.machine)
    val entry = HistoryEntry(
        title = "${result.machine} result",
        detail = "${result.title}: ${result.detail} Cost: $cost BetaTokens.",
        minutesDelta = result.minutesDelta,
        kind = "gambling",
    )
    return copy(
        lockedUntilMillis = newLockedUntil,
        betaTokens = max(0, betaTokens - cost),
        history = listOf(entry) + history,
    )
}

private fun AppState.buyShopMercy(minutes: Int, cost: Int): AppState {
    if (betaTokens < cost) return this
    val base = max(lockedUntilMillis, System.currentTimeMillis())
    val newLockedUntil = max(System.currentTimeMillis(), base - minutes.minutesMillis)
    val entry = HistoryEntry(
        title = "Shop mercy bought",
        detail = "-${formatPenaltyLabel(minutes)} bought for $cost BetaTokens.",
        minutesDelta = -minutes,
        kind = "shop",
    )
    return copy(
        lockedUntilMillis = newLockedUntil,
        betaTokens = betaTokens - cost,
        history = listOf(entry) + history,
    )
}

private fun resolvePrizeMinutes(prize: Prize, lockedUntilMillis: Long): Int {
    val now = System.currentTimeMillis()
    val currentRemainingMinutes = ((max(lockedUntilMillis, now) - now) / 60_000L).toInt()
    return when (prize.minutesDelta) {
        Int.MIN_VALUE -> currentRemainingMinutes.coerceAtMost(3.daysMinutes)
        Int.MIN_VALUE + 1 -> currentRemainingMinutes
        Int.MAX_VALUE -> Random.nextInt(14.daysMinutes, 21.daysMinutes + 1)
        else -> prize.minutesDelta
    }
}

private fun rouletteResult(number: Int, colorBets: Set<RouletteBetColor>, numberBets: Set<Int>): GamblingResult {
    val color = rouletteBetColor(number)
    val stake = colorBets.size + numberBets.size
    val winnings = colorBets.count { it == color } * 2 + numberBets.count { it == number } * 36
    val profitUnits = winnings - stake
    val detail = "Landed $number ${rouletteColorLabel(number)}. Bets: ${rouletteBetSummary(colorBets, numberBets)}."
    return when {
        profitUnits > 0 -> GamblingResult(
            title = "Roulette Paid Out",
            detail = "$detail Profit units: +$profitUnits.",
            minutesDelta = -profitUnits.hoursMinutes.coerceAtMost(1.daysMinutes),
            machine = "Roulette",
        )
        profitUnits == 0 -> GamblingResult(
            title = "Roulette Push",
            detail = "$detail The table gave nothing and took nothing.",
            minutesDelta = 0,
            machine = "Roulette",
        )
        number == 0 && RouletteBetColor.Green !in colorBets -> GamblingResult(
            title = "Green Zero Tax",
            detail = "$detail Zero punished every bad bet.",
            minutesDelta = 1.daysMinutes,
            machine = "Roulette",
        )
        else -> GamblingResult(
            title = "Roulette Denial",
            detail = "$detail Loss units: ${-profitUnits}.",
            minutesDelta = (-profitUnits * 2).hoursMinutes,
            machine = "Roulette",
        )
    }
}

private fun rouletteIsBlack(number: Int): Boolean =
    number in setOf(2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35)

private fun rouletteIsRed(number: Int): Boolean =
    number in setOf(1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36)

private fun rouletteBetColor(number: Int): RouletteBetColor =
    when {
        number == 0 -> RouletteBetColor.Green
        rouletteIsRed(number) -> RouletteBetColor.Red
        else -> RouletteBetColor.Black
    }

private fun rouletteColorLabel(number: Int): String =
    when {
        number == 0 -> "green"
        rouletteIsRed(number) -> "red"
        else -> "black"
    }

private fun rouletteNumberColor(number: Int): Color =
    when {
        number == 0 -> Color(0xFF159447)
        rouletteIsRed(number) -> Color(0xFFB52626)
        else -> Color(0xFF111111)
    }

private fun rouletteColorMatches(number: Int, color: RouletteBetColor): Boolean =
    when (color) {
        RouletteBetColor.Green -> number == 0
        RouletteBetColor.Black -> number != 0 && rouletteIsBlack(number)
        RouletteBetColor.Red -> number != 0 && rouletteIsRed(number)
    }

private fun rouletteBetWon(number: Int, colorBets: Set<RouletteBetColor>, numberBets: Set<Int>): Boolean =
    rouletteBetColor(number) in colorBets || number in numberBets

private fun rouletteBetSummary(colorBets: Set<RouletteBetColor>, numberBets: Set<Int>): String {
    val colors = colorBets.joinToString { it.label }
    val numbers = numberBets.sorted().joinToString(prefix = "#")
    return listOf(colors, numbers).filter { it.isNotBlank() && it != "#" }.joinToString(" + ").ifBlank { "None" }
}

private fun positiveModulo(value: Float, mod: Float): Float =
    ((value % mod) + mod) % mod

private fun towerCashoutResult(floor: Int, multiplier: Double): GamblingResult {
    val minutes = when (floor) {
        1 -> -30
        2 -> -1.hoursMinutes
        3 -> -2.hoursMinutes
        4 -> -4.hoursMinutes
        5 -> -8.hoursMinutes
        6 -> -12.hoursMinutes
        7 -> -1.daysMinutes
        else -> -2.daysMinutes
    }
    return GamblingResult(
        title = "Cashed Out",
        detail = "Floor $floor at ${"%.2f".format(multiplier)}x. You ran before the tower ate you.",
        minutesDelta = minutes,
        machine = "Cage Tower",
    )
}

private fun towerCollapseResult(floor: Int): GamblingResult {
    val minutes = when (floor) {
        1 -> 2.hoursMinutes
        2 -> 4.hoursMinutes
        3 -> 8.hoursMinutes
        4 -> 12.hoursMinutes
        5 -> 1.daysMinutes
        6 -> 2.daysMinutes
        7 -> 3.daysMinutes
        else -> 5.daysMinutes
    }
    return GamblingResult(
        title = "Tower Collapsed",
        detail = "Floor $floor broke under you. Buried a little deeper.",
        minutesDelta = minutes,
        machine = "Cage Tower",
    )
}

private fun towerRewardLabel(floor: Int, multiplier: Double): String =
    "${gamblingDeltaLabel(towerCashoutResult(floor, multiplier).minutesDelta)}"

private fun dropSlots(risk: DropRisk): List<DropSlot> =
    when (risk) {
        DropRisk.Low -> listOf(
            DropSlot("+1d", 1.daysMinutes, 3, Coral),
            DropSlot("+6h", 6.hoursMinutes, 7, CoralDim),
            DropSlot("+2h", 2.hoursMinutes, 14, Violet),
            DropSlot("+1h", 1.hoursMinutes, 18, Violet.copy(alpha = 0.6f)),
            DropSlot("-30m", -30, 12, CyanDim),
            DropSlot("-1h", -1.hoursMinutes, 7, CyanDim),
            DropSlot("-6h", -6.hoursMinutes, 2, Cyan),
        )
        DropRisk.Mid -> listOf(
            DropSlot("+3d", 3.daysMinutes, 3, Coral),
            DropSlot("+1d", 1.daysMinutes, 8, CoralDim),
            DropSlot("+8h", 8.hoursMinutes, 14, Violet),
            DropSlot("+3h", 3.hoursMinutes, 18, Violet.copy(alpha = 0.6f)),
            DropSlot("-1h", -1.hoursMinutes, 10, CyanDim),
            DropSlot("-4h", -4.hoursMinutes, 5, Cyan),
            DropSlot("-1d", -1.daysMinutes, 1, Mint),
        )
        DropRisk.High -> listOf(
            DropSlot("+7d", 7.daysMinutes, 4, Coral),
            DropSlot("+3d", 3.daysMinutes, 8, CoralDim),
            DropSlot("+1d", 1.daysMinutes, 14, Violet),
            DropSlot("+12h", 12.hoursMinutes, 20, Violet.copy(alpha = 0.6f)),
            DropSlot("-3h", -3.hoursMinutes, 7, CyanDim),
            DropSlot("-12h", -12.hoursMinutes, 3, Cyan),
            DropSlot("-2d", -2.daysMinutes, 1, Mint),
        )
    }

private fun weightedDropSlot(slots: List<DropSlot>): Int {
    val total = slots.sumOf { it.weight }
    var roll = Random.nextInt(total)
    slots.forEachIndexed { index, slot ->
        roll -= slot.weight
        if (roll < 0) return index
    }
    return slots.lastIndex
}

private fun DropSlot.toGamblingResult(): GamblingResult =
    GamblingResult(
        title = "Lock Drop ${label}",
        detail = if (minutesDelta >= 0) "The ball found punishment." else "The ball found a mercy pocket.",
        minutesDelta = minutesDelta,
        machine = "Lock Drop",
    )

private fun doubleOdds(attempt: Int): Int =
    listOf(45, 40, 36, 33).getOrElse(attempt) { 30 }

private fun improveGamblingResult(result: GamblingResult, attempt: Int): GamblingResult {
    val improved = when {
        result.minutesDelta < 0 -> result.minutesDelta * 2
        result.minutesDelta > 0 -> result.minutesDelta / 2
        else -> -30
    }
    return result.copy(
        title = "Double Won x$attempt",
        detail = "The button behaved. The next one gets worse.",
        minutesDelta = improved,
    )
}

private fun worsenGamblingResult(result: GamblingResult, attempt: Int): GamblingResult {
    val worsened = when {
        result.minutesDelta < 0 -> -result.minutesDelta * 2
        result.minutesDelta > 0 -> result.minutesDelta * 2
        else -> 2.hoursMinutes
    }
    return result.copy(
        title = "Double Lost x$attempt",
        detail = "That beautiful button ate the result and spat sentence back.",
        minutesDelta = worsened,
    )
}

private fun doubleFailureResult(result: GamblingResult, attempt: Int): GamblingResult {
    val basePunishment = listOf(
        1.hoursMinutes,
        2.hoursMinutes,
        4.hoursMinutes,
        6.hoursMinutes,
        12.hoursMinutes,
        1.daysMinutes,
    ).random()
    val finalPunishment = basePunishment * 2
    return GamblingResult(
        title = "Double Betrayed",
        detail = "The house rolled +${formatPenaltyLabel(basePunishment)} and doubled it for pressing the button.",
        minutesDelta = finalPunishment,
        machine = result.machine,
    )
}

private fun gamblingDeltaLabel(minutes: Int): String =
    when {
        minutes == 0 -> "No change"
        minutes > 0 -> "+${formatPenaltyLabel(minutes)}"
        else -> "-${formatPenaltyLabel(-minutes)}"
    }

private fun AppState.casePrizes(): List<Prize> = prizeTables.getValue(selectedCase)

private fun drawPrize(prizes: List<Prize>): Prize {
    val total = prizes.sumOf { it.weight }
    var roll = Random.nextInt(total)
    for (prize in prizes) {
        roll -= prize.weight
        if (roll < 0) return prize
    }
    return prizes.first()
}

private fun Prize.oddsText(prizes: List<Prize>): String {
    val percent = weight * 100.0 / prizes.sumOf { it.weight }
    return "%.2f%%".format(percent)
}

private fun generateReel(winningPrize: Prize?, prizes: List<Prize>): List<Prize> {
    val reel = MutableList(60) { visualPrize(prizes) }
    if (winningPrize != null) {
        reel[WinningPrizeIndex] = winningPrize
    }
    return reel
}

private fun visualPrize(prizes: List<Prize>): Prize {
    val roll = Random.nextFloat()
    val pool = when {
        roll < 0.55f -> prizes.filter { it.rarity == Rarity.MilSpec }
        roll < 0.80f -> prizes.filter { it.rarity == Rarity.Restricted }
        roll < 0.93f -> prizes.filter { it.rarity == Rarity.Classified }
        roll < 0.99f -> prizes.filter { it.rarity == Rarity.Covert }
        else -> prizes.filter { it.rarity == Rarity.Gold }
    }.ifEmpty { prizes }
    return pool.random()
}

private fun rarityColor(rarity: Rarity): Color = when (rarity) {
    Rarity.MilSpec -> RarityMilSpec
    Rarity.Restricted -> RarityRestricted
    Rarity.Classified -> RarityClassified
    Rarity.Covert -> RarityCovert
    Rarity.Gold -> RarityGold
}

private fun formatDuration(milliseconds: Long): String {
    val totalSeconds = milliseconds / 1_000
    val days = totalSeconds / 86_400
    val hours = (totalSeconds % 86_400) / 3_600
    val minutes = (totalSeconds % 3_600) / 60
    val seconds = totalSeconds % 60
    return if (days > 0) {
        "${days}d ${hours}h ${minutes}m"
    } else {
        "%02d:%02d:%02d".format(hours, minutes, seconds)
    }
}

private fun formatPenaltyLabel(minutes: Int): String =
    when {
        minutes % 1.daysMinutes == 0 -> "${minutes / 1.daysMinutes}d"
        minutes % 60 == 0 -> "${minutes / 60}h"
        else -> "${minutes}m"
    }

private fun hourLabel(hour: Int): String = "${hour.coerceIn(0, 23).toString().padStart(2, '0')}:00"

private fun taskRewardLine(task: DailyTask): String =
    when (task.validationStatus) {
        MissionValidationStatus.Validated -> {
            val difficulty = task.difficulty?.label ?: "Validated"
            "$difficulty - ${task.rewardTokens} BetaTokens"
        }
        MissionValidationStatus.Rejected -> "Rejected - no BetaTokens"
        MissionValidationStatus.Failed -> "Failed yesterday - +10m penalty"
        MissionValidationStatus.Draft -> "Needs Gemini validation"
    }

private fun taskStatusColor(task: DailyTask): Color =
    when (task.validationStatus) {
        MissionValidationStatus.Validated -> Cyan
        MissionValidationStatus.Rejected -> Coral
        MissionValidationStatus.Failed -> Coral
        MissionValidationStatus.Draft -> Muted
    }

private fun parseMissionDifficulty(value: String?): MissionDifficulty? =
    when (value?.trim()?.lowercase()?.replace("-", "")?.replace("_", "")?.replace(" ", "")) {
        "easy" -> MissionDifficulty.Easy
        "medium", "normal" -> MissionDifficulty.Medium
        "hard" -> MissionDifficulty.Hard
        "hardcore", "appendhardcore" -> MissionDifficulty.Hardcore
        else -> null
    }

private fun gamblingCostTokens(machine: String): Int =
    when (machine) {
        "Roulette" -> RouletteCostTokens
        "Cage Tower" -> TowerCostTokens
        "Lock Drop" -> DropCostTokens
        else -> 0
    }

private fun isQuietHour(hour: Int, start: Int, end: Int): Boolean {
    val safeHour = hour.coerceIn(0, 23)
    val safeStart = start.coerceIn(0, 23)
    val safeEnd = end.coerceIn(0, 23)
    return when {
        safeStart == safeEnd -> false
        safeStart < safeEnd -> safeHour in safeStart until safeEnd
        else -> safeHour >= safeStart || safeHour < safeEnd
    }
}

private fun formatDateTime(milliseconds: Long): String {
    val formatter = DateTimeFormatter.ofPattern("MMM d, HH:mm")
    return Instant.ofEpochMilli(milliseconds).atZone(ZoneId.systemDefault()).format(formatter)
}

private fun formatShortDate(milliseconds: Long): String {
    val formatter = DateTimeFormatter.ofPattern("MMM d")
    return Instant.ofEpochMilli(milliseconds).atZone(ZoneId.systemDefault()).format(formatter)
}

private fun formatHoursLabel(hours: Int): String {
    return if (hours % 24 == 0) {
        val days = hours / 24
        "$days day${if (days == 1) "" else "s"}"
    } else {
        "$hours hour${if (hours == 1) "" else "s"}"
    }
}

private fun todayKey(): String = LocalDate.now(ZoneId.systemDefault()).toString()

private object GeminiMissionClient {
    suspend fun validate(
        apiKey: String,
        tasks: List<DailyTask>,
    ): List<MissionValidationResult> = withContext(Dispatchers.IO) {
        val taskList = JSONArray().apply {
            tasks.forEach { task ->
                put(
                    JSONObject()
                        .put("id", task.id)
                        .put("title", task.title)
                        .put("currently_completed", task.completed)
                        .put("source", task.missionSource.name.lowercase())
                )
            }
        }
        val request = JSONObject()
            .put(
                "systemInstruction",
                JSONObject().put(
                    "parts",
                    JSONArray().put(
                        JSONObject().put(
                            "text",
                            """
                            You validate solo app missions for a private gamified chastity timer.
                            Judge whether each mission is real, actionable, non-duplicate, and not obvious low-effort credit farming.
                            Short natural-language missions are allowed. Do not reject a task just because it is brief when the action is understandable.
                            Accept ordinary real-life tasks such as "shower", "clean dishes", or "talk with a girl" as valid when they are plausible for the user to do.
                            This app allows consensual adult kink missions, including chastity, edging, orgasm control, femdom roleplay, humiliation roleplay, and mild/moderate self-controlled CBT-style tasks.
                            Do not reject a mission solely because it mentions CBT, genital impact, pain, discomfort, or kink punishment.
                            Reject only if the mission is impossible, nonsensical, duplicate, credit-farming, non-consensual, or asks for serious injury/permanent harm.
                            Rank each valid mission as exactly one of: easy, medium, hard, hardcore.
                            easy means quick/light but still real. medium means normal effort. hard means meaningful effort. hardcore means unusually demanding or time-consuming.
                            Return JSON only.
                            """.trimIndent()
                        )
                    )
                )
            )
            .put(
                "contents",
                JSONArray().put(
                    JSONObject()
                        .put("role", "user")
                        .put(
                            "parts",
                            JSONArray().put(
                                JSONObject().put(
                                    "text",
                                    """
                                    Validate this full mission board in one pass.
                                    Return exactly:
                                    {
                                      "missions": [
                                        {"id":"same id", "valid":true, "difficulty":"easy|medium|hard|hardcore", "reason":"short reason"}
                                      ]
                                    }

                                    Missions:
                                    $taskList
                                    """.trimIndent()
                                )
                            )
                        )
                )
            )
            .put(
                "generationConfig",
                JSONObject()
                    .put("responseMimeType", "application/json")
                    .put("temperature", 0.2)
            )
            .put(
                "safetySettings",
                JSONArray()
                    .put(geminiSafety("HARM_CATEGORY_HARASSMENT"))
                    .put(geminiSafety("HARM_CATEGORY_HATE_SPEECH"))
                    .put(geminiSafety("HARM_CATEGORY_SEXUALLY_EXPLICIT"))
                    .put(geminiSafety("HARM_CATEGORY_DANGEROUS_CONTENT"))
                    .put(geminiSafety("HARM_CATEGORY_CIVIC_INTEGRITY"))
            )

        var lastException: Exception? = null
        for (model in DORO_FALLBACK_MODELS) {
            try {
                return@withContext parseMissionValidations(callGemini(apiKey, model, request))
            } catch (error: GeminiHttpException) {
                if (isRetryableHttpError(error.responseCode, error.responseBody)) {
                    lastException = error
                    continue
                }
                throw error
            }
        }
        throw lastException ?: IllegalStateException("All Gemini models exhausted")
    }

    private fun parseMissionValidations(responseText: String): List<MissionValidationResult> {
        val root = JSONObject(responseText)
        val text = root
            .getJSONArray("candidates")
            .getJSONObject(0)
            .getJSONObject("content")
            .getJSONArray("parts")
            .getJSONObject(0)
            .getString("text")
            .trim()
            .removePrefix("```json")
            .removePrefix("```")
            .removeSuffix("```")
            .trim()
        val json = JSONObject(text)
        val missions = json.optJSONArray("missions") ?: JSONArray()
        return missions.mapObjects { item ->
            val valid = item.optBoolean("valid", false)
            val difficulty = parseMissionDifficulty(item.optString("difficulty"))
            MissionValidationResult(
                id = item.getString("id"),
                valid = valid && difficulty != null,
                difficulty = difficulty,
                reason = item.optString("reason", if (valid) "Validated." else "Rejected."),
            )
        }
    }
}

private fun geminiSafety(category: String): JSONObject =
    JSONObject().put("category", category).put("threshold", "BLOCK_NONE")

private object GeminiProofClient {
    suspend fun verify(
        context: Context,
        apiKey: String,
        code: String,
        bitmap: Bitmap,
    ): ProofVerdict = withContext(Dispatchers.IO) {
        val masterPrompt = context.assets.open("gemini.txt").bufferedReader().use { it.readText() }
        val imageBase64 = bitmap.toJpegBase64()
        val request = JSONObject()
            .put(
                "systemInstruction",
                JSONObject().put(
                    "parts",
                    JSONArray().put(JSONObject().put("text", masterPrompt))
                )
            )
            .put(
                "contents",
                JSONArray().put(
                    JSONObject()
                        .put("role", "user")
                        .put(
                            "parts",
                            JSONArray()
                                .put(
                                    JSONObject().put(
                                        "text",
                                        """
                                        Evaluate this proof check.
                                        Required code: $code
                                        Required subject: a chastity cage or lock device visibly being worn.
                                        The required code must be visible in the photo itself, handwritten or displayed on another device. Do not count the prompt text as code visibility.
                                        challenge_satisfied should be true when both the required code and required subject are visible; no extra gesture is required for this check.
                                        Return JSON only with:
                                        code_matches, required_subject_visible, challenge_satisfied, confidence, verdict, reason.
                                        verdict must be PASSED only if code_matches, required_subject_visible, and challenge_satisfied are all true.
                                        """.trimIndent()
                                    )
                                )
                                .put(
                                    JSONObject().put(
                                        "inlineData",
                                        JSONObject()
                                            .put("mimeType", "image/jpeg")
                                            .put("data", imageBase64)
                                    )
                                )
                        )
                )
            )
            .put(
                "generationConfig",
                JSONObject()
                    .put("responseMimeType", "application/json")
                    .put("temperature", 0.1)
            )
            .put(
                "safetySettings",
                JSONArray()
                    .put(safety("HARM_CATEGORY_HARASSMENT"))
                    .put(safety("HARM_CATEGORY_HATE_SPEECH"))
                    .put(safety("HARM_CATEGORY_SEXUALLY_EXPLICIT"))
                    .put(safety("HARM_CATEGORY_DANGEROUS_CONTENT"))
                    .put(safety("HARM_CATEGORY_CIVIC_INTEGRITY"))
            )

        var lastException: Exception? = null
        for (model in DORO_FALLBACK_MODELS) {
            try {
                return@withContext parseVerdict(callGemini(apiKey, model, request))
            } catch (error: GeminiHttpException) {
                if (isRetryableHttpError(error.responseCode, error.responseBody)) {
                    lastException = error
                    continue
                }
                throw error
            }
        }
        throw lastException ?: IllegalStateException("All Gemini models exhausted")
    }

    private fun safety(category: String): JSONObject =
        JSONObject().put("category", category).put("threshold", "BLOCK_NONE")

    private fun parseVerdict(responseText: String): ProofVerdict {
        val root = JSONObject(responseText)
        val text = root
            .getJSONArray("candidates")
            .getJSONObject(0)
            .getJSONObject("content")
            .getJSONArray("parts")
            .getJSONObject(0)
            .getString("text")
            .trim()
            .removePrefix("```json")
            .removePrefix("```")
            .removeSuffix("```")
            .trim()
        val json = JSONObject(text)
        val verdict = json.optString("verdict", "FAILED").uppercase()
        return ProofVerdict(
            passed = verdict == "PASSED" || verdict == "PASS",
            confidence = json.optDouble("confidence", 0.0).coerceIn(0.0, 1.0),
            reason = json.optString("reason", "No reason returned."),
        )
    }
}

private fun Bitmap.toJpegBase64(): String {
    val output = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 88, output)
    return Base64.encodeToString(output.toByteArray(), Base64.NO_WRAP)
}

private object AppStore {
    private const val Prefs = "roulete_store"
    private const val StateKey = "state"
    private const val GeminiApiKey = "gemini_api_key"

    fun load(context: Context): AppState {
        val database = BetalockerDatabase.get(context)
        val fromRoom = loadRoom(context, database.dao())
        if (fromRoom != null) return fromRoom

        val json = context.getSharedPreferences(Prefs, Context.MODE_PRIVATE).getString(StateKey, null)
        val state = if (json == null) seedState() else loadLegacyJson(json).getOrElse { seedState() }
        save(context, state)
        return state
    }

    fun save(context: Context, state: AppState) {
        context.getSharedPreferences(Prefs, Context.MODE_PRIVATE)
            .edit()
            .putString(GeminiApiKey, state.geminiApiKey)
            .putString(StateKey, state.toLegacyJson())
            .apply()

        val dao = BetalockerDatabase.get(context).dao()
        dao.insertMeta(state.toMetaEntity())
        dao.clearMissions()
        dao.insertMissions(state.tasks.map { it.toEntity() })
        dao.clearHistory()
        dao.insertHistory(state.history.take(150).map { it.toEntity() })
        dao.clearProofLogs()
        dao.insertProofLogs(state.proofHistory.take(80).map { it.toEntity() })
        dao.clearTokenTransactions()
        dao.insertTokenTransactions(tokenTransactionsFromHistory(state.history))
    }

    private fun seedState(): AppState = AppState(
        tasks = listOf(
            templateTask("Morning check-in", MissionDifficulty.Easy),
            templateTask("Workout or stretch", MissionDifficulty.Medium),
            templateTask("Evening reflection", MissionDifficulty.Easy),
        ),
        history = listOf(
            HistoryEntry(
                title = "Prototype ready",
                detail = "Choose a starting sentence to begin.",
                minutesDelta = 0,
                kind = "system",
            )
        ),
    )

    private fun loadRoom(context: Context, dao: BetalockerDao): AppState? {
        val meta = dao.getMeta() ?: return null
        val prefs = context.getSharedPreferences(Prefs, Context.MODE_PRIVATE)
        return AppState(
            tasks = dao.getMissions().map { it.toDailyTask() },
            lockedUntilMillis = meta.lockedUntilMillis,
            betaTokens = meta.betaTokens,
            history = dao.getHistory().map { it.toHistoryEntry() },
            discreetMode = meta.discreetMode,
            lastResetDay = meta.lastResetDay,
            onboardingComplete = meta.onboardingComplete,
            tutorialComplete = meta.tutorialComplete,
            strictMode = meta.strictMode,
            geminiApiKey = prefs.getString(GeminiApiKey, null)
                ?: prefs.getString(StateKey, null)?.let { legacy ->
                    runCatching { JSONObject(legacy).optString("geminiApiKey", "") }.getOrDefault("")
                }.orEmpty(),
            proofChecksEnabled = meta.proofChecksEnabled,
            proofCheck = meta.proofCheckJson?.let(::proofCheckFromJson),
            proofChancePercentPerHour = meta.proofChancePercentPerHour,
            proofQuietStartHour = meta.proofQuietStartHour,
            proofQuietEndHour = meta.proofQuietEndHour,
            proofFailurePenaltyMinutes = meta.proofFailurePenaltyMinutes,
            proofHistory = dao.getProofLogs().map { it.toProofLog() },
            selectedCase = runCatching { CaseType.valueOf(meta.selectedCase) }.getOrDefault(CaseType.Denial),
        )
    }

    private fun loadLegacyJson(json: String): Result<AppState> = runCatching {
        val root = JSONObject(json)
        AppState(
            tasks = root.getJSONArray("tasks").mapObjects { task ->
                val legacyRewardBoxes = task.optInt("rewardBoxes", 0)
                val difficulty = runCatching {
                    MissionDifficulty.valueOf(task.optString("difficulty"))
                }.getOrNull() ?: legacyDifficulty(legacyRewardBoxes)
                val status = runCatching {
                    MissionValidationStatus.valueOf(task.optString("validationStatus"))
                }.getOrNull() ?: if (task.has("rewardTokens") || legacyRewardBoxes > 0) {
                    MissionValidationStatus.Validated
                } else {
                    MissionValidationStatus.Draft
                }
                val source = runCatching {
                    MissionSource.valueOf(task.optString("missionSource", MissionSource.Custom.name))
                }.getOrDefault(MissionSource.Custom)
                DailyTask(
                    id = task.getString("id"),
                    title = task.getString("title"),
                    rewardTokens = task.optInt("rewardTokens", difficulty?.tokens ?: 0),
                    completed = task.getBoolean("completed"),
                    rewardClaimedDay = task.optString("rewardClaimedDay").ifBlank { null },
                    validationStatus = status,
                    difficulty = difficulty,
                    validationReason = task.optString("validationReason").ifBlank { "Migrated from old task rewards." },
                    missionSource = source,
                )
            },
            lockedUntilMillis = root.getLong("lockedUntilMillis"),
            betaTokens = if (root.has("betaTokens")) root.optInt("betaTokens", 0) else root.optInt("availableBoxes", 0) * CaseCostTokens,
            history = root.getJSONArray("history").mapObjects { entry ->
                HistoryEntry(
                    id = entry.getString("id"),
                    title = entry.getString("title"),
                    detail = entry.getString("detail"),
                    minutesDelta = entry.getInt("minutesDelta"),
                    kind = entry.getString("kind"),
                    rarity = entry.optString("rarity").ifBlank { null },
                    timestampMillis = entry.getLong("timestampMillis"),
                )
            },
            discreetMode = root.optBoolean("discreetMode", false),
            lastResetDay = root.optString("lastResetDay", todayKey()),
            onboardingComplete = root.optBoolean("onboardingComplete", false),
            tutorialComplete = root.optBoolean("tutorialComplete", false),
            strictMode = root.optBoolean("strictMode", false),
            geminiApiKey = root.optString("geminiApiKey", ""),
            proofChecksEnabled = root.optBoolean("proofChecksEnabled", false),
            proofChancePercentPerHour = root.optInt("proofChancePercentPerHour", 8).coerceIn(0, 100),
            proofQuietStartHour = root.optInt("proofQuietStartHour", 0).coerceIn(0, 23),
            proofQuietEndHour = root.optInt("proofQuietEndHour", 0).coerceIn(0, 23),
            proofFailurePenaltyMinutes = root.optInt("proofFailurePenaltyMinutes", 0).let { minutes ->
                if (minutes in listOf(0, 30, 60, 1.daysMinutes)) minutes else 0
            },
            proofHistory = root.optJSONArray("proofHistory")?.mapObjects { log ->
                ProofLog(
                    id = log.optString("id").ifBlank { UUID.randomUUID().toString() },
                    code = log.getString("code"),
                    passed = log.getBoolean("passed"),
                    confidence = log.optDouble("confidence", 0.0).coerceIn(0.0, 1.0),
                    reason = log.optString("reason", "No reason stored."),
                    penaltyMinutes = log.optInt("penaltyMinutes", 0),
                    timestampMillis = log.optLong("timestampMillis", System.currentTimeMillis()),
                )
            } ?: emptyList(),
            selectedCase = runCatching { CaseType.valueOf(root.optString("selectedCase", CaseType.Denial.name)) }.getOrDefault(CaseType.Denial),
            proofCheck = root.optJSONObject("proofCheck")?.let { proof ->
                ProofCheck(
                    code = proof.getString("code"),
                    frozenRemainingMillis = proof.getLong("frozenRemainingMillis"),
                    startedAtMillis = proof.getLong("startedAtMillis"),
                    attempts = proof.optInt("attempts", 0),
                    lastMessage = proof.optString("lastMessage").ifBlank { null },
                )
            },
        )
    }
}

private fun AppState.toLegacyJson(): String = JSONObject().apply {
    put(
        "tasks",
        JSONArray().apply {
            tasks.forEach { task ->
                val taskJson = JSONObject()
                    .put("id", task.id)
                    .put("title", task.title)
                    .put("rewardTokens", task.rewardTokens)
                    .put("completed", task.completed)
                    .put("validationStatus", task.validationStatus.name)
                    .put("missionSource", task.missionSource.name)
                task.rewardClaimedDay?.let { taskJson.put("rewardClaimedDay", it) }
                task.difficulty?.let { taskJson.put("difficulty", it.name) }
                task.validationReason?.let { taskJson.put("validationReason", it) }
                put(taskJson)
            }
        },
    )
    put("lockedUntilMillis", lockedUntilMillis)
    put("betaTokens", betaTokens)
    put(
        "history",
        JSONArray().apply {
            history.forEach { entry ->
                val entryJson = JSONObject()
                    .put("id", entry.id)
                    .put("title", entry.title)
                    .put("detail", entry.detail)
                    .put("minutesDelta", entry.minutesDelta)
                    .put("kind", entry.kind)
                    .put("timestampMillis", entry.timestampMillis)
                entry.rarity?.let { entryJson.put("rarity", it) }
                put(entryJson)
            }
        },
    )
    put("discreetMode", discreetMode)
    put("lastResetDay", lastResetDay)
    put("onboardingComplete", onboardingComplete)
    put("tutorialComplete", tutorialComplete)
    put("strictMode", strictMode)
    put("geminiApiKey", geminiApiKey)
    put("proofChecksEnabled", proofChecksEnabled)
    put("proofChancePercentPerHour", proofChancePercentPerHour)
    put("proofQuietStartHour", proofQuietStartHour)
    put("proofQuietEndHour", proofQuietEndHour)
    put("proofFailurePenaltyMinutes", proofFailurePenaltyMinutes)
    put(
        "proofHistory",
        JSONArray().apply {
            proofHistory.forEach { log ->
                put(
                    JSONObject()
                        .put("id", log.id)
                        .put("code", log.code)
                        .put("passed", log.passed)
                        .put("confidence", log.confidence)
                        .put("reason", log.reason)
                        .put("penaltyMinutes", log.penaltyMinutes)
                        .put("timestampMillis", log.timestampMillis),
                )
            }
        },
    )
    put("selectedCase", selectedCase.name)
    proofCheck?.let { put("proofCheck", JSONObject(it.toJsonString())) }
}.toString()

private fun AppState.toMetaEntity(): AppMetaEntity =
    AppMetaEntity(
        "state",
        lockedUntilMillis,
        betaTokens,
        discreetMode,
        lastResetDay,
        onboardingComplete,
        tutorialComplete,
        strictMode,
        "",
        proofChecksEnabled,
        proofCheck?.toJsonString(),
        proofChancePercentPerHour.coerceIn(0, 100),
        proofQuietStartHour.coerceIn(0, 23),
        proofQuietEndHour.coerceIn(0, 23),
        proofFailurePenaltyMinutes,
        selectedCase.name,
        0L,
        null,
        null,
        2,
        0,
    )

private fun DailyTask.toEntity(): MissionEntity =
    MissionEntity(
        id = id,
        title = title,
        rewardTokens = rewardTokens,
        completed = completed,
        rewardClaimedDay = rewardClaimedDay,
        validationStatus = validationStatus.name,
        difficulty = difficulty?.name,
        validationReason = validationReason,
        missionSource = missionSource.name,
    )

private fun MissionEntity.toDailyTask(): DailyTask =
    DailyTask(
        id = id,
        title = title,
        rewardTokens = rewardTokens,
        completed = completed,
        rewardClaimedDay = rewardClaimedDay,
        validationStatus = runCatching { MissionValidationStatus.valueOf(validationStatus) }.getOrDefault(MissionValidationStatus.Draft),
        difficulty = difficulty?.let { runCatching { MissionDifficulty.valueOf(it) }.getOrNull() },
        validationReason = validationReason,
        missionSource = runCatching { MissionSource.valueOf(missionSource) }.getOrDefault(MissionSource.Custom),
    )

private fun HistoryEntry.toEntity(): HistoryEntity =
    HistoryEntity(
        id = id,
        title = title,
        detail = detail,
        minutesDelta = minutesDelta,
        kind = kind,
        rarity = rarity,
        timestampMillis = timestampMillis,
    )

private fun HistoryEntity.toHistoryEntry(): HistoryEntry =
    HistoryEntry(
        id = id,
        title = title,
        detail = detail,
        minutesDelta = minutesDelta,
        kind = kind,
        rarity = rarity,
        timestampMillis = timestampMillis,
    )

private fun ProofLog.toEntity(): ProofLogEntity =
    ProofLogEntity(
        id = id,
        code = code,
        passed = passed,
        confidence = confidence,
        reason = reason,
        penaltyMinutes = penaltyMinutes,
        timestampMillis = timestampMillis,
    )

private fun ProofLogEntity.toProofLog(): ProofLog =
    ProofLog(
        id = id,
        code = code,
        passed = passed,
        confidence = confidence,
        reason = reason,
        penaltyMinutes = penaltyMinutes,
        timestampMillis = timestampMillis,
    )

private fun ProofCheck.toJsonString(): String =
    JSONObject()
        .put("code", code)
        .put("frozenRemainingMillis", frozenRemainingMillis)
        .put("startedAtMillis", startedAtMillis)
        .put("attempts", attempts)
        .put("lastMessage", lastMessage)
        .toString()

private fun proofCheckFromJson(json: String): ProofCheck? =
    runCatching {
        val proof = JSONObject(json)
        ProofCheck(
            code = proof.getString("code"),
            frozenRemainingMillis = proof.getLong("frozenRemainingMillis"),
            startedAtMillis = proof.getLong("startedAtMillis"),
            attempts = proof.optInt("attempts", 0),
            lastMessage = proof.optString("lastMessage").ifBlank { null },
        )
    }.getOrNull()

private fun tokenTransactionsFromHistory(history: List<HistoryEntry>): List<TokenTransactionEntity> =
    history.mapNotNull { entry ->
        val earned = Regex("""earned (\d+) BetaTokens""").find(entry.detail)?.groupValues?.getOrNull(1)?.toIntOrNull()
        val spent = Regex("""Cost: (\d+) BetaTokens|for (\d+) BetaTokens""").find(entry.detail)?.groupValues
            ?.drop(1)
            ?.firstNotNullOfOrNull { it.toIntOrNull() }
        val delta = when {
            earned != null -> earned
            spent != null -> -spent
            else -> null
        } ?: return@mapNotNull null
        TokenTransactionEntity(
            id = entry.id,
            label = entry.title,
            delta = delta,
            source = entry.kind,
            timestampMillis = entry.timestampMillis,
        )
    }.take(120)

private fun templateTask(title: String, difficulty: MissionDifficulty): DailyTask =
    DailyTask(
        title = title,
        rewardTokens = difficulty.tokens,
        validationStatus = MissionValidationStatus.Validated,
        difficulty = difficulty,
        validationReason = "Starter mission.",
        missionSource = MissionSource.Template,
    )

private fun legacyDifficulty(rewardBoxes: Int): MissionDifficulty? =
    when (rewardBoxes) {
        1 -> MissionDifficulty.Easy
        2 -> MissionDifficulty.Medium
        3, 4 -> MissionDifficulty.Hard
        in 5..Int.MAX_VALUE -> MissionDifficulty.Hardcore
        else -> null
    }

private inline fun <T> JSONArray.mapObjects(block: (JSONObject) -> T): List<T> {
    val values = mutableListOf<T>()
    for (index in 0 until length()) values += block(getJSONObject(index))
    return values
}

private val Ink = Color(0xFF07080B)
private val Surface0 = Color(0xFF10121A)
private val Surface1 = Color(0xFF181C27)
private val Surface2 = Color(0xFF1F2435)
private val Surface3 = Color(0xFF252A3E)
private val Rim = Color(0x12FFFFFF)
private val Divider = Color(0x0DFFFFFF)
private val Muted = Color(0xFFB7BDC6)
private val Cyan = Color(0xFF78F5FF)
private val CyanDim = Color(0x3378F5FF)
private val CyanGlow = Color(0x6078F5FF)
private val CyanOnDark = Color(0xFFB8FEFF)
private val Gold = Color(0xFFFFC857)
private val GoldDim = Color(0x30FFC857)
private val Coral = Color(0xFFFF6B6B)
private val CoralDim = Color(0x25FF6B6B)
private val Mint = Color(0xFF68FFCE)
private val MintDim = Color(0x2568FFCE)
private val Violet = Color(0xFFC09AFF)
private val VioletDim = Color(0x25C09AFF)
private val Gold2 = Color(0xFFFFD700)
private val RarityMilSpec = Color(0xFF6DBAFF)
private val RarityRestricted = Color(0xFFC09AFF)
private val RarityClassified = Color(0xFFFFC857)
private val RarityCovert = Color(0xFFFF6B6B)
private val RarityGold = Color(0xFFFFD700)
// Phase 1 compat aliases — screens still reference these until later phases migrate
private val Panel = Surface1
private val PanelAlt = Surface2
private val Charcoal = Surface3
