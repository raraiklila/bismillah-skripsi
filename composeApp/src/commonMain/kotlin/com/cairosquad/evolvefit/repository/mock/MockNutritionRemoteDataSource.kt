package com.cairosquad.evolvefit.repository.mock

import com.cairosquad.evolvefit.domain.model.MealType
import com.cairosquad.evolvefit.repository.nutrition.remote.NutritionRemoteDataSource
import com.cairosquad.evolvefit.repository.nutrition.remote.dto.ConsumedMealDto
import com.cairosquad.evolvefit.repository.nutrition.remote.dto.ConsumedMealRequestDto
import com.cairosquad.evolvefit.repository.nutrition.remote.dto.DailyCalorieSummaryDto
import com.cairosquad.evolvefit.repository.nutrition.remote.dto.DailyWaterSummaryDto
import com.cairosquad.evolvefit.repository.nutrition.remote.dto.FavouriteMealDto
import com.cairosquad.evolvefit.repository.nutrition.remote.dto.MealDto
import com.cairosquad.evolvefit.repository.nutrition.remote.dto.SuggestedMealDto

class MockNutritionRemoteDataSource : NutritionRemoteDataSource {

    // ── In-memory state ─────────────────────────────────────────────────────
    private val dailyCaloriesGoal = 2000
    private val dailyWaterGoal = 2.5f

    // State yang berubah saat user tambah makanan / air
    private var consumedCalories: Int = 1250
    private var consumedWaterLiters: Float = 1.5f

    // Riwayat makanan yang ditambahkan sesi ini
    private val consumedMeals = mutableListOf<ConsumedMealDto>(
        ConsumedMealDto(
            id = "consumed-1",
            userId = "user-1",
            date = "2026-09-21T08:30:00",
            caloriesConsumed = 280,
            mealName = "Oatmeal with Berries",
            mealType = "BREAKFAST"
        ),
        ConsumedMealDto(
            id = "consumed-2",
            userId = "user-1",
            date = "2026-09-21T12:30:00",
            caloriesConsumed = 350,
            mealName = "Grilled Chicken Salad",
            mealType = "LUNCH"
        ),
        ConsumedMealDto(
            id = "consumed-3",
            userId = "user-1",
            date = "2026-09-21T18:30:00",
            caloriesConsumed = 420,
            mealName = "Salmon with Vegetables",
            mealType = "DINNER"
        ),
        ConsumedMealDto(
            id = "consumed-4",
            userId = "user-1",
            date = "2026-09-21T20:00:00",
            caloriesConsumed = 200,
            mealName = "Greek Yogurt",
            mealType = "SNACK"
        )
    )

    // Favorites
    private val favourites = mutableListOf<FavouriteMealDto>()

    // ── Suggested Meals (statis) ─────────────────────────────────────────────
    private val suggestedMeals = listOf(
        SuggestedMealDto(
            id = "meal-1",
            name = "Grilled Chicken Salad",
            type = MealType.LUNCH,
            calories = 350,
            imageUrl = "https://images.unsplash.com/photo-1546793665-c74683f339c1?w=400"
        ),
        SuggestedMealDto(
            id = "meal-2",
            name = "Oatmeal with Berries",
            type = MealType.BREAKFAST,
            calories = 280,
            imageUrl = "https://images.unsplash.com/photo-1517673132405-a56a62b18caf?w=400"
        ),
        SuggestedMealDto(
            id = "meal-3",
            name = "Salmon with Vegetables",
            type = MealType.DINNER,
            calories = 420,
            imageUrl = "https://images.unsplash.com/photo-1467003909585-2f8a72700288?w=400"
        ),
        SuggestedMealDto(
            id = "meal-4",
            name = "Greek Yogurt",
            type = MealType.SNACK,
            calories = 150,
            imageUrl = "https://images.unsplash.com/photo-1488477181946-6428a0291777?w=400"
        ),
        SuggestedMealDto(
            id = "meal-5",
            name = "Brown Rice & Egg",
            type = MealType.BREAKFAST,
            calories = 320,
            imageUrl = "https://images.unsplash.com/photo-1536304929831-ee1ca9d44906?w=400"
        ),
        SuggestedMealDto(
            id = "meal-6",
            name = "Tuna Sandwich",
            type = MealType.LUNCH,
            calories = 390,
            imageUrl = "https://images.unsplash.com/photo-1528735602780-2552fd46c7af?w=400"
        ),
    )

    private val mealDetailsMap = mapOf(
        "meal-1" to MealDto(
            id = "meal-1",
            name = "Grilled Chicken Salad",
            description = "A healthy grilled chicken salad with fresh vegetables.",
            calories = 350,
            carbs = 20,
            protein = 35,
            fat = 12,
            type = MealType.LUNCH,
            ingredients = listOf("Chicken breast", "Lettuce", "Tomato", "Cucumber", "Olive oil"),
            imageUrl = "https://images.unsplash.com/photo-1546793665-c74683f339c1?w=400"
        ),
        "meal-2" to MealDto(
            id = "meal-2",
            name = "Oatmeal with Berries",
            description = "Warm oatmeal topped with fresh mixed berries and honey.",
            calories = 280,
            carbs = 52,
            protein = 8,
            fat = 5,
            type = MealType.BREAKFAST,
            ingredients = listOf("Oats", "Blueberries", "Strawberries", "Honey", "Milk"),
            imageUrl = "https://images.unsplash.com/photo-1517673132405-a56a62b18caf?w=400"
        ),
        "meal-3" to MealDto(
            id = "meal-3",
            name = "Salmon with Vegetables",
            description = "Pan-seared salmon with seasonal roasted vegetables.",
            calories = 420,
            carbs = 18,
            protein = 40,
            fat = 20,
            type = MealType.DINNER,
            ingredients = listOf("Salmon fillet", "Broccoli", "Carrot", "Olive oil", "Lemon"),
            imageUrl = "https://images.unsplash.com/photo-1467003909585-2f8a72700288?w=400"
        ),
        "meal-4" to MealDto(
            id = "meal-4",
            name = "Greek Yogurt",
            description = "Creamy Greek yogurt rich in protein.",
            calories = 150,
            carbs = 10,
            protein = 15,
            fat = 4,
            type = MealType.SNACK,
            ingredients = listOf("Greek yogurt", "Honey", "Granola"),
            imageUrl = "https://images.unsplash.com/photo-1488477181946-6428a0291777?w=400"
        ),
        "meal-5" to MealDto(
            id = "meal-5",
            name = "Brown Rice & Egg",
            description = "Nutritious brown rice served with boiled eggs.",
            calories = 320,
            carbs = 45,
            protein = 14,
            fat = 8,
            type = MealType.BREAKFAST,
            ingredients = listOf("Brown rice", "Eggs", "Soy sauce", "Sesame oil"),
            imageUrl = "https://images.unsplash.com/photo-1536304929831-ee1ca9d44906?w=400"
        ),
        "meal-6" to MealDto(
            id = "meal-6",
            name = "Tuna Sandwich",
            description = "Classic tuna sandwich with whole grain bread.",
            calories = 390,
            carbs = 35,
            protein = 30,
            fat = 14,
            type = MealType.LUNCH,
            ingredients = listOf("Tuna", "Whole grain bread", "Lettuce", "Mayo", "Lemon"),
            imageUrl = "https://images.unsplash.com/photo-1528735602780-2552fd46c7af?w=400"
        ),
    )

    // ── Override functions ───────────────────────────────────────────────────

    override suspend fun getSuggestedMeals(): List<SuggestedMealDto> = suggestedMeals

    override suspend fun getFavouriteMeals(): List<FavouriteMealDto> = favourites.toList()

    override suspend fun addFavouriteMealById(mealId: String) {
        val meal = suggestedMeals.find { it.id == mealId } ?: return
        if (favourites.none { it.id == mealId }) {
            favourites.add(
                FavouriteMealDto(
                    id = meal.id,
                    name = meal.name,
                    type = meal.type,
                    calories = meal.calories,
                    imageUrl = meal.imageUrl
                )
            )
        }
    }

    override suspend fun deleteFavouriteMeal(mealId: String) {
        favourites.removeAll { it.id == mealId }
    }

    override suspend fun getMealHistory(
        startDate: String,
        endDate: String
    ): List<ConsumedMealDto> = consumedMeals.toList()

    override suspend fun getConsumedMealsByDate(
        startDate: String,
        endDate: String
    ): List<ConsumedMealDto> = consumedMeals.toList()

    override suspend fun getMealById(id: String): MealDto {
        return mealDetailsMap[id] ?: mealDetailsMap.values.first()
    }

    override suspend fun saveConsumedMeal(consumedMealRequestDto: ConsumedMealRequestDto): Boolean {
        // Update total kalori
        consumedCalories += consumedMealRequestDto.consumedCalories

        // Tambahkan ke riwayat
        consumedMeals.add(
            ConsumedMealDto(
                id = "consumed-${consumedMeals.size + 1}",
                userId = "user-1",
                date = "2026-09-04T13:00:00",
                caloriesConsumed = consumedMealRequestDto.consumedCalories,
                mealName = consumedMealRequestDto.mealName,
                mealType = consumedMealRequestDto.mealType
            )
        )
        return true
    }

    override suspend fun getDailyCalorieSummary(): DailyCalorieSummaryDto {
        return DailyCalorieSummaryDto(
            totalCalories = dailyCaloriesGoal,
            consumedCalories = consumedCalories
        )
    }

    override suspend fun saveConsumedWater(amountLiters: Float): Boolean {
        // Update total air
        consumedWaterLiters += amountLiters
        return true
    }

    override suspend fun getDailyWaterSummary(): DailyWaterSummaryDto {
        return DailyWaterSummaryDto(
            consumedWater = consumedWaterLiters,
            totalWater = dailyWaterGoal
        )
    }
}
