package com.example.a7minutesworkout

object Constants {

    fun defaultExerciseList():ArrayList<ExerciseModel>{
        val exerciseList=ArrayList<ExerciseModel>()

        val jumpingJacks=ExerciseModel(1,
        "Jumping Jacks",
            R.drawable.jumpingjacks,
            false,
            false
        )

        exerciseList.add(jumpingJacks)

        val wallSit=ExerciseModel(2,
            "Wall Sit",
            R.drawable.wallsit,
            false,
            false
        )

        exerciseList.add(wallSit)

        val pushUp=ExerciseModel(3,
            "Push Up",
            R.drawable.pushup,
            false,
            false
        )

        exerciseList.add(pushUp)

        val abdominalCrunch=ExerciseModel(4,
            "Abdominal Crunch",
            R.drawable.abdominalcrunch,
            false,
            false
        )

        exerciseList.add(abdominalCrunch)

        val stepUpOnChair=ExerciseModel(5,
            "Step Up On Chair",
            R.drawable.stepupontochair,
            false,
            false
        )

        exerciseList.add(stepUpOnChair)

        val squat=ExerciseModel(6,
            "Squat",
            R.drawable.squat,
            false,
            false
        )

        exerciseList.add(squat)

        val tricepDip=ExerciseModel(7,
            "Triceps Dip on Chair",
            R.drawable.tricepdips,
            false,
            false
        )

        exerciseList.add(tricepDip)

        val plank=ExerciseModel(8,
            "Plank",
            R.drawable.plank,
            false,
            false
        )

        exerciseList.add(plank)

        val highKneesRunning=ExerciseModel(9,
            "High Knees Running In Place",
            R.drawable.runinplace,
            false,
            false
        )

        exerciseList.add(highKneesRunning)

        val lunges=ExerciseModel(10,
            "Lunges",
            R.drawable.lunge,
            false,
            false
        )

        exerciseList.add(lunges)

        val pushupAndRotation=ExerciseModel(11,
            "Push up and Rotation",
            R.drawable.pushuprot,
            false,
            false
        )

        exerciseList.add(pushupAndRotation)

        val sidePlank=ExerciseModel(12,
            "Side Plank",
            R.drawable.sideplank,
            false,
            false
        )

        exerciseList.add(sidePlank)


        return exerciseList
    }
}