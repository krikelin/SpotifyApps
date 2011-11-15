package com.krikelin.mako;

public interface IScriptEngine
{
    /**
    /// <summary>
    /// This function is called by the preprocesor after the code has been compiled from the preprocessing template
    /// </summary>
    /// <param name="scriptCode">The string with the javascript code</param>
    /// <returns>True if sucess, false if failed </returns>*/
    public String run(String scriptCode);

    /// <summary>
    /// Invoke an function in the client script from the executable code
    /// </summary>
    /// <param name="Function"></param>
    /// <param name="arguments"></param>
    /// <returns></returns>
    public Object invoke(String Function,Object... arguments);

    /// <summary>
    /// Thus method is called to make an instance of an certain object accessible for the scripting object.
    /// </summary>
    /// <param name="variableName"></param>
    /// <param name="varInstance"></param>
    public void setVariable(String variableName, Object varInstance);

    /// <summary>
    /// This method is called to set an function to be accessible by the functionName for the script once Executed.
    /// </summary>
    /// <param name="functionName"></param>
    /// <param name="functionPointer"></param>
    public void setFunction(String functionName, Delegate functionPointer);

   
}
