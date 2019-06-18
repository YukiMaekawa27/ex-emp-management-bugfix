/**
 * 
 */
$(function() {
    $('.employees').pagination({
        itemElement : '> .employee', // アイテムの要素
        paginationClassName  : 'pagination',
        paginationInnerClassName  :  'pagination',
        prevNextPageBtnMode            : false,
		firstEndPageBtnMode            : false
    });
});