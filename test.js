function onLoad()
{
    RhythmWheels.setNumberOfWheels(2)
    RhythmWheels.lockNumberOfWheels()
}

function onUnload()
{
    print("Unloaded")
}

function onWheelsComplete()
{
    print('Completed');
}

function onPlay()
{
    print('Play button pressed');
}