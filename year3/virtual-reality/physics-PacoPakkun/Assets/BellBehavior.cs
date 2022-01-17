using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BellBehavior : MonoBehaviour
{
    AudioSource source;

    void Start()
    {
        source = GetComponent<AudioSource>();
    }

    private void OnCollisionEnter(Collision collision)
    {
        source.Play();
    }
}