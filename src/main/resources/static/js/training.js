/**
 * HTMLタグを除去する。
 *
 * @param sentence 対象の文字列
 * @returns {string} HTMLタグを除去した文字列
 */
function removeTags(sentence){
    sentence = sentence.replace(/<("[^"]*"|'[^']*'|[^'">])*>/g,'');
    return sentence;
}