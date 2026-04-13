package com.faridev.coinollar.data.mapper

import com.faridev.coinollar.core.network.model.CryptoCurrencyDto
import com.faridev.coinollar.core.network.model.CurrenciesResponseDto
import com.faridev.coinollar.core.network.model.GoldAndCurrencyDto
import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyMapperTest {

    @Test
    fun `GoldAndCurrencyDto toDomain maps correctly`() {
        val dto = GoldAndCurrencyDto(
            changePercent = 2.5,
            changeValue = 50000,
            date = "1404/01/01",
            name = "دلار آمریکا",
            nameEn = "US Dollar",
            price = 75000,
            symbol = "USD",
            time = "14:30",
            timeUnix = 1704067200,
            unit = "تومان"
        )

        val domain = dto.toDomain()

        assertEquals("USD", domain.symbol)
        assertEquals(75000.0, domain.price, 0.01)
        assertEquals(50000.0, domain.changeValue!!, 0.01)
        assertEquals(2.5, domain.changePercent, 0.01)
        assertEquals("دلار آمریکا", domain.name)
    }

    @Test
    fun `CryptoCurrencyDto toDomain maps price from String`() {
        val dto = CryptoCurrencyDto(
            changePercent = -1.2,
            date = "1404/01/01",
            name = "بیت کوین",
            nameEn = "Bitcoin",
            price = "95234.56",
            symbol = "BTC",
            time = "14:30",
            timeUnix = 1704067200,
            unit = "دلار"
        )

        val domain = dto.toDomain()

        assertEquals("BTC", domain.symbol)
        assertEquals(95234.56, domain.price, 0.01)
        assertEquals(null, domain.changeValue)
    }

    @Test
    fun `CryptoCurrencyDto toDomain handles invalid price gracefully`() {
        val dto = CryptoCurrencyDto(
            changePercent = 0.0,
            date = "1404/01/01",
            name = "تست",
            nameEn = "Test",
            price = "invalid",
            symbol = "TEST",
            time = "14:30",
            timeUnix = 1704067200,
            unit = "دلار"
        )

        val domain = dto.toDomain()

        assertEquals(0.0, domain.price, 0.01)
    }

    @Test
    fun `CurrenciesResponseDto toEntities creates correct categories`() {
        val response = CurrenciesResponseDto(
            cryptoCurrency = listOf(
                CryptoCurrencyDto(
                    changePercent = 1.0, date = "", name = "", nameEn = "BTC",
                    price = "100", symbol = "BTC", time = "", timeUnix = 0, unit = ""
                )
            ),
            currency = listOf(
                GoldAndCurrencyDto(
                    changePercent = 1.0, changeValue = 10, date = "", name = "", nameEn = "USD",
                    price = 75000, symbol = "USD", time = "", timeUnix = 0, unit = ""
                )
            ),
            gold = listOf(
                GoldAndCurrencyDto(
                    changePercent = 1.0, changeValue = 100, date = "", name = "", nameEn = "Gold",
                    price = 15000000, symbol = "IR_GOLD_18K", time = "", timeUnix = 0, unit = ""
                )
            )
        )

        val entities = response.toEntities()

        assertEquals(3, entities.size)
        assertEquals("crypto", entities[0].category)
        assertEquals("currency", entities[1].category)
        assertEquals("gold", entities[2].category)
    }
}
