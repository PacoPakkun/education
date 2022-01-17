using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class WalkMovement : MonoBehaviour
{
    private float x = 0f;   
    private float v = 1f;

    void Start()
    {
    }

    // Update is called once per frame
    void Update()
    {
        x += v * Time.deltaTime;
        transform.localPosition = new Vector3(x, transform.localPosition.y, transform.localPosition.z);
    }
}