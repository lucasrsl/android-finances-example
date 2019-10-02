package com.example.fincancesk.model

import java.math.BigDecimal
import java.util.Calendar

class Transaction (val value: BigDecimal,
                   val category: String = "Indefinida",
                   val type: Type,
                   val date: Calendar = Calendar.getInstance())