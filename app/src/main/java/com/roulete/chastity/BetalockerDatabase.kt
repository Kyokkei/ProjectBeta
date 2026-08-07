package com.roulete.chastity

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Entity(tableName = "app_meta")
data class AppMetaEntity(
    @PrimaryKey val id: String = "state",
    val lockedUntilMillis: Long,
    val betaTokens: Int,
    val discreetMode: Boolean,
    val lastResetDay: String,
    val onboardingComplete: Boolean,
    val tutorialComplete: Boolean,
    val strictMode: Boolean,
    val geminiModel: String,
    val proofChecksEnabled: Boolean,
    val proofCheckJson: String?,
    val proofChancePercentPerHour: Int,
    val proofQuietStartHour: Int,
    val proofQuietEndHour: Int,
    val proofFailurePenaltyMinutes: Int,
    val selectedCase: String,
    val prejacLockedUntilMillis: Long,
    val prejacMediaUri: String?,
    val prejacMediaType: String?,
    val prejacRoundMinutes: Int,
    val prejacFailures: Int,
)

@Entity(tableName = "missions")
data class MissionEntity(
    @PrimaryKey val id: String,
    val title: String,
    val rewardTokens: Int,
    val completed: Boolean,
    val rewardClaimedDay: String?,
    val validationStatus: String,
    val difficulty: String?,
    val validationReason: String?,
    val missionSource: String,
)

@Entity(tableName = "history_entries")
data class HistoryEntity(
    @PrimaryKey val id: String,
    val title: String,
    val detail: String,
    val minutesDelta: Int,
    val kind: String,
    val rarity: String?,
    val timestampMillis: Long,
)

@Entity(tableName = "proof_logs")
data class ProofLogEntity(
    @PrimaryKey val id: String,
    val code: String,
    val passed: Boolean,
    val confidence: Double,
    val reason: String,
    val penaltyMinutes: Int,
    val timestampMillis: Long,
)

@Entity(tableName = "token_transactions")
data class TokenTransactionEntity(
    @PrimaryKey val id: String,
    val label: String,
    val delta: Int,
    val source: String,
    val timestampMillis: Long,
)

@Dao
interface BetalockerDao {
    @Query("SELECT * FROM app_meta WHERE id = 'state' LIMIT 1")
    fun getMeta(): AppMetaEntity?

    @Query("SELECT * FROM missions")
    fun getMissions(): List<MissionEntity>

    @Query("SELECT * FROM history_entries ORDER BY timestampMillis DESC")
    fun getHistory(): List<HistoryEntity>

    @Query("SELECT * FROM proof_logs ORDER BY timestampMillis DESC")
    fun getProofLogs(): List<ProofLogEntity>

    @Query("SELECT * FROM token_transactions ORDER BY timestampMillis DESC")
    fun getTokenTransactions(): List<TokenTransactionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeta(meta: AppMetaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMissions(missions: List<MissionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history: List<HistoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProofLogs(logs: List<ProofLogEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTokenTransactions(transactions: List<TokenTransactionEntity>)

    @Query("DELETE FROM missions")
    fun clearMissions()

    @Query("DELETE FROM history_entries")
    fun clearHistory()

    @Query("DELETE FROM proof_logs")
    fun clearProofLogs()

    @Query("DELETE FROM token_transactions")
    fun clearTokenTransactions()
}

@Database(
    entities = [
        AppMetaEntity::class,
        MissionEntity::class,
        HistoryEntity::class,
        ProofLogEntity::class,
        TokenTransactionEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class BetalockerDatabase : RoomDatabase() {
    abstract fun dao(): BetalockerDao

    companion object {
        @Volatile private var instance: BetalockerDatabase? = null

        fun get(context: Context): BetalockerDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    BetalockerDatabase::class.java,
                    "betalocker.db",
                )
                    .allowMainThreadQueries()
                    .build()
                    .also { instance = it }
            }
    }
}
