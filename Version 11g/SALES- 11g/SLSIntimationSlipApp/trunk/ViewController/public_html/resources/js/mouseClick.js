clickEventBean = function(event)
{
AdfCustomEvent.queue(event.getSource(), "clickEventBean", {}, false);
return true;
}