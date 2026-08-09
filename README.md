# betalocker

**Yahallo betas**

Free solo chastity app for locked boys who want the *system* to control them instead of a real keyholder. Or you just too shy to ask someone. Either way. You're here.

You make your own daily missions. You can't just spam trash to farm tokens though. Every mission has to pass AI check first. If it's stupid, too easy, or obvious spam, it gets rejected. No free money glitch.

Earn **BetaTokens** by actually doing valid stuff, then dump them in the **Gambling zone**. It's a whole mini casino and the house is *not* on your side.

There's a locked timer. Optional live cage-check photo (AI verified) that can freeze your timer if you fail or refuse. Privacy mode exists if you don't want photos. You're still locked either way.

No payments. No real keyholder needed. Just open it and stay denied.

If you cheat, that's your problem. The system doesn't care.

**Stay locked.**

---

<p align="center">
  <img src="docs/readme/Home.jpg" alt="Home" width="220" />
  &nbsp;
  <img src="docs/readme/task.jpg" alt="Tasks" width="220" />
  &nbsp;
  <img src="docs/readme/gacha.jpg" alt="Gambling zone" width="220" />
</p>

<p align="center">
  <img src="docs/readme/widget.png" alt="Widget" width="360" />
</p>

---

## The vibe

Daily discipline. Rigged rewards. Zero escape energy.

```text
get locked
   ↓
make missions → AI says yes or no
   ↓
finish them → get BetaTokens
   ↓
Gambling zone goes brr → more lock time (usually)
   ↓
optional proof pics → fail and the timer freezes on you
```

---

## What's inside

### Home
Big locked timer. Countdown until "mercy" (lol).  
Proof check toggle if you want random cage pics. Fail or ghost it and the timer freezes.  
Token count + how many missions you finished today.

![Home](docs/readme/Home.jpg)

### Tasks
Write your own missions. Hit **Validate**. AI decides if it's real work or you being lazy.  
Pass the check, finish the mission, get paid in BetaTokens.

Rough payouts:

| How hard | Tokens |
|----------|--------|
| Easy     | 5      |
| Medium   | 7      |
| Hard     | 10     |
| Hardcore | 20     |

![Tasks](docs/readme/task.jpg)

### Gambling zone (mini casino)
This is not just one gacha button. Full **Gambling zone**:

| Game | Cost | What it is |
|------|------|------------|
| **Cases** | 3 tokens | Pity / Denial / Extinction chests. Pull and cry. |
| **Roulette** | 5 tokens | Spin. Hope is a skill issue. |
| **Cage Tower** | 6 tokens | Climb the tower. Fall eventually. |
| **Lock Drop** (ball drop) | 5 tokens | Drop the ball. Watch your sentence cook. |

Odds are stacked against you on purpose. Most pulls add days. Mercy exists just enough to keep you spinning.

![Gambling zone](docs/readme/gacha.jpg)

### Shop
Burn a fat stack of tokens for tiny time cuts. Expensive crumbs. Not freedom.

### Widget
Home screen widget that shows how many hours you've been denied. Cute. Mean. Accurate.

![Widget](docs/readme/widget.png)

### Also in the app
- First-time tour so you're not lost  
- History of what the app did to you  
- Settings (API key, privacy, reset, replay tour)  
- Everything important lives on your phone (local)

---

## How to start

1. Install the app (build it or use an APK if you have one).  
2. Open it. Pick how long you start locked.  
3. Do the little tour.  
4. Add your **Gemini API key** in Settings (guide below).  
5. Make missions → Validate → finish them → go gamble badly.

Android 8+ is fine. Internet only needed when AI is checking stuff.

---

## Free Gemini API key (you need this)

Missions and optional photo checks use Google Gemini. You make your **own free key**. Nothing shady. Your key, your phone.

### Picture guide

![How to make a free Gemini API key](docs/readme/gemini_api_key_guide.jpg)

### Steps (super simple)

1. **Open Google AI Studio**  
   Go to [aistudio.google.com](https://aistudio.google.com/) and log in with Google.

2. **API Keys**  
   Menu → **API Keys** (under Developer) → hit **Create API key**.

3. **Name it**  
   Call it something like `chastity` or `betalocker`. Pick a project if it asks. Create key.

4. **Copy it**  
   Copy the key and paste it in the app: **menu → Settings → Gemini API key**.

### Don't be dumb with the key
- Free tier is enough for normal use.  
- **Don't share it.** Not in Discord, not in screenshots, not on GitHub.  
- Leaked? Delete it in AI Studio and make a new one.  
- Validation failing? Wrong key, no internet, or quota vibes. Check Settings.

AI only judges missions / proof pics. Your **timer, tokens, and casino odds** are all handled by the app itself.

---

## Token math (quick)

**Earn:** Easy 5 · Medium 7 · Hard 10 · Hardcore 20  

**Burn in Gambling zone:** Cases 3 · Roulette 5 · Cage Tower 6 · Lock Drop 5  

**Shop mercy (expensive):** −2h = 40 · −4h = 80 · −6h = 115  

Tokens are for bad decisions. Not for unlocking your life.

---

## Privacy real quick

- Lock time, missions, tokens: on **your** device  
- No real money stuff  
- No forced keyholder  
- Photo checks are **optional**. Off = still locked, just no pics  
- Pics only go out when *you* turn checks on and send one, using *your* API key  

If you unlock IRL and lie, the app can't stop you. This is for people who want the friction.

---

## Building it yourself

```bash
./gradlew :app:assembleDebug
# Windows:
gradlew.bat :app:assembleDebug
```

Devs who care: Kotlin, Compose, local DB, your own Gemini key. Deeper notes live in `PLAN.md`.

---

## Heads up

Adult consensual fetish / self-lock game. Not medical advice. Not a real lock. Not a substitute for actual safety and common sense. If it stops being fun or safe, stop. The app will not fix your life choices.

---

## Bottom line

You write the missions.  
AI rejects the easy outs.  
Gambling zone is a mini casino and it hates you.  
Timer keeps running.

No paywall. No keyholder required.  
Open it. Stay denied.

*If you cheat, that's your problem. The system doesn't care.*
