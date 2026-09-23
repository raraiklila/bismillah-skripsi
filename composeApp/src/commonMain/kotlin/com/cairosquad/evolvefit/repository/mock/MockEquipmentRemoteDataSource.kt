package com.cairosquad.evolvefit.repository.mock

import com.cairosquad.evolvefit.repository.equipment.remote.EquipmentsRemoteDataSource
import com.cairosquad.evolvefit.repository.equipment.remote.dto.GymEquipmentDto

class MockEquipmentRemoteDataSource : EquipmentsRemoteDataSource {
    override suspend fun getEquipments(): List<GymEquipmentDto> {
        return listOf(
            GymEquipmentDto(id = 1, name = "Barbell"),
            GymEquipmentDto(id = 2, name = "Dumbbell"),
            GymEquipmentDto(id = 3, name = "Resistance Band"),
            GymEquipmentDto(id = 4, name = "Kettlebell"),
            GymEquipmentDto(id = 5, name = "Pull-up Bar"),
            GymEquipmentDto(id = 6, name = "Bench"),
            GymEquipmentDto(id = 7, name = "Treadmill"),
            GymEquipmentDto(id = 8, name = "Jump Rope"),
        )
    }
}
