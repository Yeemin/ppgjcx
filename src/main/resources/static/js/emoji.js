; (function ($) {

    var countryEmoji = {
        cn: '🇨🇳'
    }

    var categoryEmoji = {

    }

    var customEmoji = {
        love: '❤️'
    }

    $.extend({

        initEmoji: function() {
            $.initCountryEmoji()
            $.initCategoryEmoji()
            $.initCustomEmoji()
        },  

        initCategoryEmoji: function () {
            let flags = $('span[category-emoji]')
            flags.map((index, item, self) => {
                let flag = $(item).attr('category-emoji')
                $(item).html(categoryEmoji[flag])
            })
        },

        initCountryEmoji: function () {
            let flags = $('span[country-emoji]')
            flags.map((index, item, self) => {
                let flag = $(item).attr('country-emoji')
                $(item).html(countryEmoji[flag])
            })
        },

        initCustomEmoji: function() {
            let flags = $('span[custom-emoji]')
            flags.map((index, item, self) => {
                let flag = $(item).attr('custom-emoji')
                $(item).html(customEmoji[flag])
            })
        }

    })

})(jQuery);