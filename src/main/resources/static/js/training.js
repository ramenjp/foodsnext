function removeTags(sentence){
    sentence = sentence.replace(/<("[^"]*"|'[^']*'|[^'">])*>/g,'');
    return sentence;
}